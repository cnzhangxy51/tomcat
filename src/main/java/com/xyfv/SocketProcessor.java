package com.xyfv;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketProcessor implements Runnable {
    private Socket socket;

    public SocketProcessor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            processSocket(socket);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void processSocket(Socket socket) throws IOException, ServletException {
        // 解析数据
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        inputStream.read(bytes);

        // 方法
        String[] methods = processBytes(0, ' ', bytes);
        int pos = Integer.parseInt(methods[0]);
        String method = methods[1];

        // uri
        String[] uris = processBytes(pos, ' ', bytes);
        pos = Integer.parseInt(uris[0]);
        String uri = uris[1];

        // 协议版本
        String protocol = processBytes(pos, '\r', bytes)[1];
        //        System.out.println(method);
        //        System.out.println(uri);
        //        System.out.println(protocol);

        Request request = new Request(method, uri, protocol, socket);
        Response response = new Response(request);
        // 找到对应的servlet
        Test1Servlet t1 = new Test1Servlet();
        t1.service(request, response);
        response.complete();
    }

    public String[] processBytes(int begin, char end, byte[] bytes) {
        StringBuilder target = new StringBuilder();
        String[] ss = new String[2];
        for (; begin < bytes.length; begin++) {
            if (bytes[begin] == end) {
                break;
            }
            target.append((char) bytes[begin]);
        }
        ss[0] = "" + (begin + 1);
        ss[1] = target.toString();
        return ss;
    }


}
