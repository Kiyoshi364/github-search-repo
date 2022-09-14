package com.kiyoshi364.gsr;

import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.NumberFormatException;
import java.net.ServerSocket;
import java.net.Socket;

public final class Server {
    private final Config config;

    private static enum Options {
        TOKEN(0, "token"), PORT(1, "port");

        public final int val;
        public final String str;
        private Options(int val, String str) {
            this.val = val;
            this.str = str;
        }
    }

    private static final String[] options = {
        Options.TOKEN.str, Options.PORT.str,
    };

    public static final class Config {
        public static final int DEFAULT_PORT = 8000;

        public final int port;
        public final String token;

        public Config(String[] args) {
            final Map<Integer, String> parsed
                = Args.parse(args, options);

            final String port_str = parsed.getOrDefault(
                        Options.PORT.val, System.getenv("PORT"));
            int port = DEFAULT_PORT;
            if ( port_str != null ) {
                try {
                    port = Integer.parseUnsignedInt(port_str);
                } catch (NumberFormatException e) {
                    System.out.println("WARNING:"
                            + " unable to parse PORT"
                            + " environment variable: `"
                            + port_str
                            + "'");
                    port = DEFAULT_PORT;
                }
            }
            this.port = port;

            String token_str = System.getenv("TOKEN");
            this.token = parsed.getOrDefault(
                        Options.TOKEN.val, System.getenv("TOKEN"));
        }
    }

    public Server(Config config) {
        this.config = config;
    }

    public Server(String[] args) {
        this(new Config(args));
    }

    public static void dropInput(Socket s, InputStream in)
            throws IOException {
        while ( s.isInputShutdown() ) {
            long toskip = in.available();
            in.skip(toskip);
        }
    }

    public static String readInput(InputStream in)
            throws IOException {
        BufferedReader buffer = new BufferedReader(
                new InputStreamReader(in));
        StringBuilder b = new StringBuilder();
        String line = buffer.readLine();
        while ( line != null && line.length() > 0 ) {
            System.out.println(line);
            b.append(line);
            b.append("\n");
            line = buffer.readLine();
        }
        System.out.println();
        return b.toString();
    }

    public void run() throws IOException {
        System.out.println("Running . . .");

        ServerSocket server = null;
        try {
            server = new ServerSocket(this.config.port);
        } catch (IOException e) {
            throw new IOException("Could not create Server", e);
        }

        try {
            while ( true ) {
                Socket socket = server.accept();
                System.out.println(">>>> New client <<<<\n");

                // TODO: Note: maybe use threads?
                InputStream in = socket.getInputStream();
                String request = readInput(in);
                System.out.println(request);

                // TODO: build parameters
                GitSearchRequest req = new GitSearchRequest("crlf");
                GitSearchResponse resp;
                PrintWriter out =
                    new PrintWriter(socket.getOutputStream());
                try {
                    resp = req.makeRequest(this.config.token);

                    String resp_str = resp.toString();

                    if ( resp_str.length() < 1000 ) {
                        out.println(resp_str);
                    } else {
                        out.println(resp_str.substring(0, 1000));
                    }
                } catch (Web.NotOkException e) {
                    out.printf("Request Response: %d %s\n",
                            e.response.rcode, e.response.rmsg);
                    out.println("Body:");
                    out.println(e.response.response);
                } finally {
                    out.flush();
                    socket.shutdownOutput();
                    dropInput(socket, in);
                    socket.close();
                    System.out.println("<<<< Response Sent >>>>\n");
                }
            }
        } catch (IOException e) {
            throw new IOException("Could not handle connection", e);
        } finally {
            server.close();
        }
    }
}
