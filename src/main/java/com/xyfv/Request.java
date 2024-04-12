package com.xyfv;

import java.net.Socket;

public class Request extends AbstractHttpServletRequest {
    private String method;
    private String uri;
    private String protocol;

    private Socket socket;

    public Request(String method, String uri, String protocol, Socket socket) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getRequestURI() {
        return uri;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }
}
