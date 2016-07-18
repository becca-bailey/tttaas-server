package com.server;

import com.server.content.FileHandler;
import com.server.routing.Router;

import java.io.*;
import java.util.Properties;

public class Server {
    private static File rootDirectory;

    public static void main(String[] args) throws Exception {
        ServerArguments arguments = new ServerArguments(args);
        int portNumber = arguments.getPortNumber();
        rootDirectory = arguments.getRootDirectory();
        try {
            loadProperties();
            addRoutes();
            ServerRunner runner = new ServerRunner(portNumber, rootDirectory);
            runner.run();
        } catch (FileNotFoundException e) {
            System.err.println("Error loading router. Make sure routesClass is defined in config.properties and implements com.rnelson.server.RouteInitializer");
        }
    }

    private static void loadProperties() throws IOException {
        Properties config = new Properties();
        String filename = "config.properties";
        try {
            InputStream input = new FileInputStream(rootDirectory.getPath() + "/config.properties");
            config.load(input);
            ServerConfig.rootDirectory = rootDirectory;
            ServerConfig.packageName = config.getProperty("packageName");
            ServerConfig.routesFile = config.getProperty("routesFile");
            input.close();
        } catch (Exception e) {
            System.err.println("Properties not found");
        }
    }

    private static void addRoutes() throws FileNotFoundException {
        ServerConfig.router = new Router(ServerConfig.rootDirectory);
        File routesFile = new File(ServerConfig.rootDirectory + "/" + ServerConfig.routesFile);
        FileHandler handler = new FileHandler(routesFile);
        String routes = new String(handler.getFileContents());
        String[] lines = routes.split("\n");
        for (String line : lines) {
            String[] splitRoute = line.split(" ");
            ServerConfig.router.addRoute(splitRoute[0], splitRoute[1]);
        }
    }
}

