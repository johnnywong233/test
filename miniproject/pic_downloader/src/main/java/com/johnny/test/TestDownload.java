package com.johnny.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestDownload {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.douban.com/photos/photo/2494383222");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            HttpURLConnection.setFollowRedirects(false);

            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.connect();
            System.out.println("【getContentLength】" + conn.getContentLength());
            System.out.println("【getContentType】" + conn.getContentType());
            InputStream in = conn.getInputStream();

            BufferedInputStream inputStream = new BufferedInputStream(in);
            //保存的实际上是 html 文件
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File("TEST")));
            byte[] data = new byte[1024];
            int n;
            while ((n = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, n);
            }
            outputStream.flush();
            in.close();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}