package com.practice.util;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * @author lxl
 * @Date 2018/5/24 11:12
 */
public class DigestUtils {
    public static String getMD5(String message,String charset) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageByte = message.getBytes(charset);
            byte[] md5Byte = md.digest(messageByte);
            md5 = bytesToHex(md5Byte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }
    /**
     * 生成MD5
     * @param message
     * @return
     */
    public static String getMD5(String message) {

        return getMD5(message,"UTF-8");
    }

    /**
     * md5
     * @param message
     * @param salt
     * @return
     */
    public static String md5(String message,String salt){
        String md5 = "";
        if (!StringUtils.isBlank(message)) {
            try {
                if (!StringUtils.isEmpty(salt)) {
                    message = message + "{" + salt + "}";
                }
                StringBuilder buffer = new StringBuilder();
                MessageDigest messageDigest = MessageDigest.getInstance("md5");
                char[] ch = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
                byte[] b = messageDigest.digest(message.getBytes());

                for(int i = 0; i < b.length; ++i) {
                    int x = b[i] >>> 4 & 15;
                    buffer.append(ch[x]);
                    x = b[i] & 15;
                    buffer.append(ch[x]);
                }

                md5 =  buffer.toString();
            } catch (Exception var8) {
                //
            }
        }

        return md5;
    }

    /**
     * 二进制转十六进制
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if(num < 0) {
                num += 256;
            }
            if(num < 16){
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}
