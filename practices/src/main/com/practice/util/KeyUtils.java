package com.practice.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * @author lxl
 * @Date 2018/4/17 17:23
 */
public class KeyUtils {
    /**
     * 字符串hash
     * @param raw
     * @return
     */
    public static Long hash(String raw){
        ByteBuffer buf = ByteBuffer.wrap(raw.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return h;
    }

    /**
     * 获取随机字符串
     * @param length
     * @param charset
     * @return
     */
    public static String randomStr(int length,String charset){
        Random random = new Random();
        byte[] randBytes = new byte[length];
        random.nextBytes(randBytes);

        return new String(randBytes, Charset.forName(charset));
    }

    /**
     * 随机字符串 编码utf-8
     * @param length
     * @return
     */
    public static String randomStr(int length){

        return randomStr(length,"UTF-8");
    }


    public static String randomStr2(int length){
        String ku = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int total = ku.length();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i < length;i++){
            sb.append(ku.charAt(random.nextInt(total)));
        }

        return sb.toString();
    }
}
