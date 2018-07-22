package io;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Test0714 {

    /**
     * from 261 java problems PDF book, 7.14
     */
    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.baidu.com/img/baidu_sylogo1.gif");
            URLConnection conn = url.openConnection();

            System.out.println(conn.getDoInput());
            System.out.println(conn.getDoOutput());
            conn.connect();

            InputStream is = conn.getInputStream();

            String file = url.getFile();
            String name = file.substring(file.lastIndexOf('/') + 1);
            System.out.println(name);
            FileOutputStream fos = new FileOutputStream(name);
            byte[] buf = new byte[1024];
            int size;
            while ((size = is.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            fos.close();
            is.close();
            conn.connect();

            System.out.println(conn.getContentLength());
            System.out.println(conn.getContentType());
            System.out.println(conn.getContentEncoding());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
