package com.kiyoshi364;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.StringBuilder;
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
        String link = search.makeLink();
        System.out.println("Search:");
        System.out.println(link);
        Web.makeRequest(link, Web.Method.GET, null);
    }
}

final class Web {

    public enum Method {
        GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE;
    }

    public static String makeRequest(
            String link, Method method, String token) {
        try {
            URL url = new URL(link);
            HttpsURLConnection conn
                = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod(method.toString());
            conn.setRequestProperty(
                    "Accept", "application/vnd.github+json");
            if ( token != null ) {
                conn.setRequestProperty(
                        "Authorization", "Bearer " + token);
            }
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setInstanceFollowRedirects(false);

            conn.connect();
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
            StringBuilder b = new StringBuilder();
            char[] buff = new char[1024];
            int read = 0;
            while ( (read = buffer.read(buff,0,buff.length)) != -1 ) {
                System.out.print(buff);
                b.append(buff);
            }
            System.out.println();
            buffer.close();
            conn.disconnect();
            return b.toString();
        } catch (MalformedURLException e) {
            System.out.println(e);
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        return null;
    }
}
