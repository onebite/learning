package com.practice.io;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author lxl
 * @Date 2018/4/26 14:34
 */
public class SSLSocketChannel {
    int SSL;
    ByteBuffer clientIn,clientOut,cTOs,sTOc,wbuf;
    SocketChannel sc = null;
    SSLEngineResult res;
    SSLEngine sslEngine;

    public SSLSocketChannel() throws IOException{
        sc = SocketChannel.open();
    }

    public SSLSocketChannel(SocketChannel sc) {
        this.sc = sc;
    }


}
