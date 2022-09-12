package com.kiyoshi364;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;

public class Main {

    public static void help_message() {
        System.out.printf("usage: <subcommand>\n");
    }

    public static void main(String[] args) {
        System.out.print("Hello World:");
        for ( String arg : args ) {
            System.out.print(" " + arg);
        }
        System.out.println();

        GitSearchRequest search = new GitSearchRequest("crlf asdf");
        System.out.println("Search:");
        System.out.println(search.makeLink());
        // Web.make_connection(
        //         search, Web.Method.GET );
    }
}

final class Web {

    public enum Method {
        GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE;
    }

    public static void make_connection(
            GitSearchRequest search, Method method) {
        String link = search.makeLink();
        System.out.printf("URL: %s\n", link);
        try {
            URL url = new URL(link);
            HttpsURLConnection conn
                = (HttpsURLConnection) url.openConnection();
            // conn.setRequestMethod(method.toString());
            // conn.setDoOutput(true);

            // conn.connect();
            int rcode = conn.getResponseCode();
            String rmsg = conn.getResponseMessage();
            System.out.printf("Response: %d %s\n",
                    rcode, rmsg);

            InputStream in = null;
            if ( rcode == 200 ) {
                in = conn.getInputStream();
            } else {
                in = conn.getErrorStream();
            }
            BufferedReader buffer
                = new BufferedReader(new InputStreamReader(in));
            String inputLine;
            while ( (inputLine = buffer.readLine()) != null ) {
                System.out.println(inputLine);
            }
            buffer.close();
        } catch (MalformedURLException e) {
            System.out.println(e);
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
}
