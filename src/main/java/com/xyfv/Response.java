package com.xyfv;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Response extends AbstractHttpServletResponse {
    private int status = 200;
    private String message = "OK";
    private Map<String, String> headers = new HashMap<>();
    private ResponseServletOutputStream bodyStream = new ResponseServletOutputStream();
    private Request request;
    private OutputStream socketOutputStream;
    private byte SP = ' ';
    private byte CR = '\r';
    private byte LF = '\n';


    public Response(Request request) throws IOException {
        this.request = request;
        this.socketOutputStream = request.getSocket().getOutputStream();
    }

    @Override
    public void setStatus(int i) {
        status = i;
    }

    @Override
    public void addHeader(String s, String s1) {
        headers.put(s, s1);
    }

    @Override
    public ResponseServletOutputStream getOutputStream() throws IOException {
        return bodyStream;
    }

    // 构造完整的响应
    public void complete() throws IOException {
        buildResponseLine(); //响应行
        buildResponseHeader(); //响应头
        buildResponseBody(); // 响应体
    }

    // 状态行（status-line）:  http协议版本号+空格+状态号+空格+状态解释+\r\n
    private void buildResponseLine() throws IOException {
        socketOutputStream.write(request.getProtocol().getBytes());
        socketOutputStream.write(SP);
        socketOutputStream.write(getCode().getBytes());
        socketOutputStream.write(SP);
        socketOutputStream.write(getMessage().getBytes());
        socketOutputStream.write(CR);
        socketOutputStream.write(LF);

    }

    // 响应头： K:V + \r\n
    private void buildResponseHeader() throws IOException {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            socketOutputStream.write(key.getBytes());
            socketOutputStream.write(":".getBytes());
            socketOutputStream.write(value.getBytes());
            socketOutputStream.write(CR);
            socketOutputStream.write(LF);
        }
        socketOutputStream.write(CR);
        socketOutputStream.write(LF);
    }


    private void buildResponseBody() throws IOException {
        socketOutputStream.write(getOutputStream().getBytes());
    }


    public String getCode() {
        return status + "";
    }

    public String getMessage() {
        return message;
    }
}
