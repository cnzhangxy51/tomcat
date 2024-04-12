package com.xyfv;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

// 响应体
public class ResponseServletOutputStream extends ServletOutputStream {

    private byte[] bytes = new byte[1024];
    private int pos = 0;

    public byte[] getBytes() {
        return bytes;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public void write(int b) throws IOException {
        bytes[pos] = (byte) b;
        pos++;
    }
}
