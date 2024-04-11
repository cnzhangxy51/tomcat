package com.xyfv;

import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketProcessor implements Runnable{
    private Socket socket;
    public SocketProcessor(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            processSocket(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processSocket(Socket socket) throws IOException {
        // 解析数据
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        inputStream.read(bytes);


    }

}
