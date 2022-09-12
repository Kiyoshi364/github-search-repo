package com.kiyoshi364.gsr;

import com.google.gson.Gson;
import java.io.IOException;

public final class Main {

    public static void main(String[] args) {

        Server.Config config = new Server.Config(args);
        System.out.print("PORT: ");
        System.out.println(config.port);

        Server server = new Server(config);
        try {
            server.run();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        // GitSearchRequest search = new GitSearchRequest("crlf");
        // String link = search.makeLink();
        // System.out.print("Search: ");
        // System.out.println(link);
        // Web.Response resp
        //     // = Web.makeRequest(link, Web.Method.GET, null);
        //     = Web.fakeRequest(link, Web.Method.GET, null);
        // Gson gson = new Gson();
        // switch ( resp.rcode ) {
        //     case 200:
        //         GitSearchResponse gsr
        //             = gson.fromJson(
        //                 resp.response, GitSearchResponse.class);
        //         System.out.println("Response:");
        //         System.out.println(gsr.toString());
        //         break;
        //     case 304:
        //     case 422:
        //     case 503:
        //         System.out.printf("Response: %d %s\n",
        //             resp.rcode, resp.rmsg);
        //         System.out.println(resp.response);
        //         break;
        // }
    }
}
