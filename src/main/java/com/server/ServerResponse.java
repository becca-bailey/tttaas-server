package com.server;

import com.server.utilities.Response;

import java.util.ArrayList;
import java.util.List;

public class ServerResponse {
    private final String status;
    private final List<String> rows = new ArrayList<>();

    public ServerResponse(int status) {
        this.status = Response.status(status);
    }

    public void addHeader(String key, String value) {
        this.rows.add(key + ": " + value);
    }

    private String getResponseHeader() {
        StringBuilder response = new StringBuilder();
        response.append(status);
        String crlf = "\r\n";
        if (rows.size() > 0) {
            response.append(crlf);
            response.append(String.join(crlf, rows));
        }
        response.append(crlf);
        response.append(crlf);
        return response.toString();
    }

    public byte[] getFullResponse(String body) {
        return (getResponseHeader() + body).getBytes();
    }

    public byte[] getFullResponse() {
        return (getResponseHeader()).getBytes();
    }

}
