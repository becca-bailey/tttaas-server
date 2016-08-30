package com.server;

import com.server.utilities.exceptions.ServerException;

import java.io.File;

public class Server {

    public static void main(String[] args) {
        ServerArguments arguments = new ServerArguments(args);
        int portNumber = arguments.getPortNumber();
        try {
            File rootDirectory = arguments.getRootDirectory();
            ServerRunner runner = new ServerRunner(portNumber, rootDirectory);
            System.out.println("Server is running on port " + portNumber + "\n");
            runner.run();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
}

