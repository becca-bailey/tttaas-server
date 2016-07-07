package com.server;

import com.server.routing.RouteInitializer;
import com.server.routing.Router;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("Error loading router. Make sure routesClass is defined in config.properties and implements com.rnelson.server.RouteInitializer");
        }
    }

    private static void loadProperties() throws IOException {
        Properties config = new Properties();
        String filename = "config.properties";
        try {
            InputStream input = new FileInputStream(rootDirectory.getPath() + "/config.properties");
            config.load(input);
            ServerConfig.packageName = config.getProperty("packageName");
            ServerConfig.routesClass = config.getProperty("routesClass");
            input.close();
        } catch (Exception e) {
            System.err.println("Properties not found");
        }
    }

    private static void addRoutes() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ServerConfig.router = new Router(ServerConfig.rootDirectory);
        Class initializerClass = Class.forName(ServerConfig.routesClass);
        RouteInitializer initializer = (RouteInitializer) initializerClass.newInstance();
        initializer.initializeRoutes(ServerConfig.router);
    }
}

