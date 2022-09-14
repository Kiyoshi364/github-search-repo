package com.kiyoshi364.gsr;

import java.io.IOException;
import java.io.PrintStream;

public final class Main {

    private static void printHelp() {
        PrintStream o = System.out;
        o.println("usage:");
        o.println("    java -jar <this jar> [args]");
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
        o.println("If \"cli\" is the first argument,");
        o.println("it makes the request and prints in the stdout");
        o.println("Try \"java -jar <this jar> cli help\"");
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
        o.println("  port=<uint>");
        o.println("        which port to use");
    }

    public static void main(String[] args) {
        if ( args.length > 0 ) {
            if ( args[0].equals("cli") ) {
                String[] cli_args = new String[args.length-1];
                for ( int i = 0; i < cli_args.length; i++ ) {
                    cli_args[i] = args[i+1];
                }
                Cli.main(cli_args);
                return;
            } else if ( Args.hasHelp(args) ) {
                printHelp();
                return;
            }
        }

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
    }
}
