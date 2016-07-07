package com.server.controller;

import com.server.ResponseData;

public interface Controller {

    byte[] get();

    byte[] head();

    byte[] post();

    byte[] put();

    byte[] patch();

    byte[] options();

    byte[] delete();

    void sendResponseData(ResponseData responseData);
}
