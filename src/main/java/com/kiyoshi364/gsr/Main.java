package com.kiyoshi364.gsr;

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
    }
}
