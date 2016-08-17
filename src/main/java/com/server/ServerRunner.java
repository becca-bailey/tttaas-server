package com.server;

import com.server.controller.Controller;
import com.server.request.Request;
import com.server.routing.Route;
import com.server.utilities.Response;
import com.server.utilities.exceptions.RouterException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;

class ServerRunner implements Runnable {
    private final int serverPort;
    private Boolean running = true;
    private final File rootDirectory;

    ServerRunner(int port, File rootDirectory) {
        this.serverPort = port;
        this.rootDirectory = rootDirectory;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (running) {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setSoTimeout(100);
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                respondToRequest(out, in);
                clientSocket.close();
            }
            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    private String getFullRequest(BufferedReader in) throws IOException {
        StringBuilder request = new StringBuilder();
        int mychar;
        try {
            while ((mychar = in.read()) != -1) {
                request.append((char) mychar);
            }
        } catch(Exception e) {
            return request.toString();
        }
        return request.toString();
    }

    private void respondToRequest (DataOutputStream out, BufferedReader in) throws IOException {
        byte[] response = new byte[0];
        String requestString = getFullRequest(in);
        if (requestString == null || requestString == "null" || requestString.length() < 5) {
            System.out.println("null request");
        } else {
            Request request = new Request(requestString);
            try {
                System.out.println("Request: " + request.method() + " " + request.url());
                Route route = routeForUrl(request.url());
                ResponseData responseData = new ResponseData(request, route);
                Controller controller = controllerForRoute(route);
                controller.sendResponseData(responseData);
                Supplier<byte[]> controllerAction = getControllerActionForRequest(controller, request.method());
                response = getResponse(controllerAction);
            } catch (RouterException e) {
                System.err.println(e.getMessage());
                response = Response.notFound.getBytes();
            }
            out.write(response);
        }
        out.close();
    }

    private Route routeForUrl(String url) throws RouterException {
        return ServerConfig.router.getExistingRoute(url);
    }

    private Controller controllerForRoute(Route route) throws RouterException {
        return ServerConfig.router.getControllerForRequest(route);
    }

    private Supplier<byte[]> getControllerActionForRequest(Controller controller, String method) {
        return ServerConfig.router.getControllerAction(controller, method);
    }

    private byte[] getResponse(Supplier<byte[]> supplier) {
        byte[] response;
        try {
            response = supplier.get();
        } catch (NullPointerException e) {
            System.err.println("Method doesn't exist in Router actions.\n");
            return Response.methodNotAllowed.getBytes();
        }
        return response;
    }

    public void stop() {
        this.running = false;
    }

    public Boolean isRunning() {
        return running;
    }
}
