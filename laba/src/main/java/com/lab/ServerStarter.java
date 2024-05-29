package com.lab;

import java.io.IOException;

import com.lab.lib.net.Server;

public class ServerStarter {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    };
}