package com.server.serverTests;

import com.server.ServerResponse;
import com.server.utilities.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class ServerResponseTest {
    ServerResponse response;

    @Before
    public void initialize() {
        response = new ServerResponse(200);
    }

    @Test
    public void addHeaderAddsAHeaderRowToTheResponse() throws Throwable {
        response.addHeader("test", "123");
        assertTrue(Arrays.equals((Response.status(200) + "\r\ntest: 123\r\n\r\n").getBytes(), response.getFullResponse()));
    }

    @Test
    public void addHeaderAddsMultipleHeadersToTheResponse() throws Throwable {
        response.addHeader("test", "123");
        response.addHeader("test", "456");
        assertTrue(Arrays.equals((Response.status(200) + "\r\ntest: 123\r\ntest: 456\r\n\r\n").getBytes(), response.getFullResponse()));
    }

    @Test
    public void getFullResponseReturnsResponseAsByteArray() throws Throwable {
        assertTrue(Arrays.equals((Response.status(200) + "\r\n\r\n").getBytes(), response.getFullResponse()));
    }

    @Test
    public void getFullResponseTakesOptionalBody() throws Throwable {
        assertTrue(Arrays.equals((Response.status(200) + "\r\n\r\ntest").getBytes(), response.getFullResponse("test")));
    }
}
