package com.kiyoshi364.gsr;

import com.google.gson.Gson;

public final class Main {

    public static void help_message() {
        System.out.printf("usage: <subcommand>\n");
    }

    public static void main(String[] args) {
        System.out.print("Hello World:");
        for ( String arg : args ) {
            System.out.print(" " + arg);
        }
        System.out.println();

        GitSearchRequest search = new GitSearchRequest("crlf");
        String link = search.makeLink();
        System.out.print("Search: ");
        System.out.println(link);
        Web.Response resp
            = Web.makeRequest(link, Web.Method.GET, null);
            // = Web.fakeRequest(link, Web.Method.GET, null);
        Gson gson = new Gson();
        switch ( resp.rcode ) {
            case 200:
                GitSearchResponse gsr
                    = gson.fromJson(
                        resp.response, GitSearchResponse.class);
                System.out.println("Response:");
                System.out.println(gsr.toString());
                break;
            case 304:
            case 422:
            case 503:
                System.out.printf("Response: %d %s\n",
                    resp.rcode, resp.rmsg);
                System.out.println(resp.response);
                break;
        }
    }
}
