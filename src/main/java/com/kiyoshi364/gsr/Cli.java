package com.kiyoshi364.gsr;

import java.io.PrintStream;
import java.lang.NumberFormatException;
import java.util.Map;
import java.util.Iterator;

public final class Cli {

    public static final String[] options = {
        "token", "q", "sort", "order", "per-page", "page"
     };

    public static void main(String[] args) {
        if ( Args.hasHelp(args) ) {
            printHelp();
            return;
        }
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

    private static void printHelp() {
        PrintStream o = System.out;
        o.println("usage:");
        o.println("    java -jar <this jar> cli q=<search> [args]");
        o.println();
        o.println("This is a program to access the"
                + " Github Search Repositories API.");
        o.println("Note that it only captures some values.");
        o.println("They are:");
        o.println("   Repo's name, description, author, language,"
                + " stars, forks and last update");
        o.println("By default it runs in the server mode,");
        o.println("and arguments serves as default parameters"
                + " to the server requests");
        o.println();
        o.println("args that needs a value are in the form"
                + " `<key>=<value>`.");
        o.println("Refer to"
                + " https://docs.github.com/en/rest/search"
                + "#search-repositories");
        o.println("for more information about the args.");
        o.println();
        o.println("Possible args:");
        o.println("  token=<string>");
        o.println("        <string> should be a valid Gitub token.");
        o.println("  q=<string>");
        o.println("        <string> to use in the repo search.");
        o.println("        This argument is required!");
        o.println("  sort="
                + "[stars|forks|help-wanted-issues|updated|best|]");
        o.println("        How to sort the results.");
        o.println("        Note that \"\" is suported and"
                + " is the same as \"best\".");
        o.println("        The default is \"stars\".");
        o.println("  order=[asc|desc]");
        o.println("        The default is \"desc\".");
        o.println("  per-page=<uint>");
        o.println("        Number of results in a page.");
        o.println("        (API's max number is 100)");
        o.println("  page=<uint>");
        o.println("        Page number.");
        o.println("        (API's default page is 1)");
        o.println("  json");
        o.println("        Prints the raw json to the stdout.");
    }
}
