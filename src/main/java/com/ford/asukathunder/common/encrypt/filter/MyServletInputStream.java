package com.ford.asukathunder.common.encrypt.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: MyServletInputStream
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:49
 **/
public class MyServletInputStream extends ServletInputStream {

    private final InputStream inputStream;

    public MyServletInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
}
