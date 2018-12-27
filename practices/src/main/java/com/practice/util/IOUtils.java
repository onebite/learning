package com.practice.util;

import java.io.*;

/**
 * @author lxl
 * @Date 2018/5/24 14:34
 */
public class IOUtils {

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] readBytes(File file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = null;
        InputStream fis = null;
        InputStream is = null;
        try {
            fis = new FileInputStream(file);
            is = new BufferedInputStream(fis);
            int count = 0;
            byte[] buf = new byte[16384];
            while ((count = is.read(buf)) != -1) {
                if (count > 0) {
                    baos.write(buf, 0, count);
                }
            }
            b = baos.toByteArray();

        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (is != null)
                    is.close();
                if (baos != null)
                    baos.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return b;
    }

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static char[] readChars(File file) throws IOException {
        CharArrayWriter caw = new CharArrayWriter();
        Reader fr = null;
        Reader in = null;
        try {
            fr = new FileReader(file);
            in = new BufferedReader(fr);
            int count = 0;
            char[] buf = new char[16384];
            while ((count = in.read(buf)) != -1) {
                if (count > 0) {
                    caw.write(buf, 0, count);
                }
            }

        } finally {
            try {
                if (caw != null)
                    caw.close();
                if (in != null)
                    in.close();
                if (fr != null)
                    fr.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return caw.toCharArray();
    }

    /**
     *
     * @param file
     * @param data
     * @throws IOException
     */
    public static void writeBytes(File file, byte[] data) throws IOException {
        OutputStream fos = null;
        OutputStream os = null;
        try {
            fos = new FileOutputStream(file);
            os = new BufferedOutputStream(fos);
            os.write(data);

        } finally {
            try {
                if (os != null)
                    os.close();
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     *
     * @param file
     * @param data
     * @throws IOException
     */
    public static void writeChars(File file, char[] data) throws IOException {
        Writer fos = null;
        Writer os = null;
        try {
            fos = new FileWriter(file);
            os = new BufferedWriter(fos);
            os.write(data);

        } finally {
            try {
                if (os != null)
                    os.close();
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
