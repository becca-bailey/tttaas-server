package com.server;

import java.io.File;

public class Server {
    private static File rootDirectory;

    public static void main(String[] args) throws Exception {
        ServerArguments arguments = new ServerArguments(args);
        int portNumber = arguments.getPortNumber();
        rootDirectory = arguments.getRootDirectory();
        try {
            ServerRunner runner = new ServerRunner(portNumber, rootDirectory);
            System.out.println("Server is running on port " + portNumber + "\n");
            runner.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

