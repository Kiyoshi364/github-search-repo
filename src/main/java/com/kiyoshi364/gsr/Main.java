package com.kiyoshi364.gsr;

import java.io.IOException;

public final class Main {

    public static void main(String[] args) {
        if ( args.length > 0 && args[0].equals("cli") ) {
            String[] cli_args = new String[args.length-1];
            for ( int i = 0; i < cli_args.length; i++ ) {
                cli_args[i] = args[i+1];
            }
            Cli.main(cli_args);
            return;
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
