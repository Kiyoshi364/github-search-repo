package com.kiyoshi364.gsr;

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

    public static final class Config {
        public static final int DEFAULT_PORT = 8000;

        public final int port;
        public final String token;

        public Config(String[] args) {
            // TODO: use args for something
            String port_str = System.getenv("PORT");
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

            // TODO: get token
            this.token = null;
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
                    out.flush();
                    dropInput(socket, in);
                } catch (Web.NotOkException e) {
                    out.printf("Request Response: %d %s\n",
                            e.response.rcode, e.response.rmsg);
                    out.println("Body:");
                    out.println(e.response.response);
                } finally {
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
