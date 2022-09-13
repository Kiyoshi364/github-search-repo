package com.kiyoshi364.gsr;

import java.util.Map;
import java.util.Iterator;
import java.lang.NumberFormatException;

public final class Cli {

    public static final String[] options = {
        "token", "q", "sort", "order", "per-page", "page"
     };

    public static void main(String[] args) {
        Map parsed = Args.parse(args, options);

        String token = null;
        String q = null;
        GitSearchRequest.Sort sort = GitSearchRequest.Sort.STARS;
        GitSearchRequest.Order order = GitSearchRequest.Order.DESC;
        Integer per_page = null;
        Integer page = null;

        final Iterator<Map.Entry<Integer, String>> it
            = parsed.entrySet().iterator();
        while ( it.hasNext() ) {
            final Map.Entry<Integer, String> e = it.next();
            final int key = e.getKey();
            final String arg = e.getValue();
            switch ( key ) {
                case 0:
                    token = arg;
                    break;
                case 1:
                    q = arg;
                    break;
                case 2:
                    if ( arg.equalsIgnoreCase("stars") ) {
                        sort = GitSearchRequest.Sort.STARS;
                    } else if ( arg.equalsIgnoreCase("forks") ) {
                        sort = GitSearchRequest.Sort.FORKS;
                    } else if ( arg.equalsIgnoreCase(
                                "help-wanted-issues") ) {
                        sort
                            = GitSearchRequest.Sort.HELP_WANTED_ISSUES;
                    } else if ( arg.equalsIgnoreCase("updated") ) {
                        sort = GitSearchRequest.Sort.UPDATED;
                    } else if ( arg.equalsIgnoreCase("")
                            || arg.equalsIgnoreCase("best") ) {
                        sort = null;
                    } else {
                        System.out.println("WARNING:"
                                + " unable to parse order"
                                + " from arg: `" + arg + "'");
                    }
                    break;
                case 3:
                    if ( arg.equalsIgnoreCase("desc") ) {
                        order = GitSearchRequest.Order.DESC;
                    } else if ( arg.equalsIgnoreCase("asc") ) {
                        order = GitSearchRequest.Order.ASC;
                    } else if ( arg.equalsIgnoreCase("") ) {
                        order = null;
                    } else {
                        System.out.println("WARNING:"
                                + " unable to parse order"
                                + " from arg: `" + arg + "'");
                    }
                    break;
                case 4:
                    try {
                        per_page = Integer.parseUnsignedInt(arg);
                    } catch (NumberFormatException ex) {
                        System.out.println("WARNING:"
                                + " unable to parse per_page"
                                + " from arg: `" + arg + "'");
                    }
                    break;
                case 5:
                    try {
                        page = Integer.parseUnsignedInt(arg);
                    } catch (NumberFormatException ex) {
                        System.out.println("WARNING:"
                                + " unable to parse page"
                                + " from arg: `" + arg + "'");
                    }
                    break;
            }
        }

        if ( q == null ) {
            System.out.println("ERROR: q argument is required!");
            System.out.println("Try adding at the end: 'q=<search>'");
            return;
        }

        GitSearchRequest req = new GitSearchRequest(
                q, sort, order, per_page, page);
        try {
            GitSearchResponse resp
                = req.makeRequest(token);
            System.out.println(resp.toString());
        } catch (Web.NotOkException e) {
            System.out.printf("Request Response: %d %s\n",
                    e.response.rcode, e.response.rmsg);
            System.out.println("Body:");
            System.out.println(e.response.response);
        }
    }

}
