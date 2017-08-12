package com.johnny.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class TetsInputStream {
    public static void main(String[] args)
            throws IOException {
        for (int i = 0; i < 10; i++) {
            String url = "https://www.douban.com/photos/photo/2494549845";
            URL image = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) image.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.connect();
            InputStream in = conn.getInputStream();

            File file = new File("D:/TEST/test" + (i + 1) + ".jpg");
            BufferedInputStream inputStream = new BufferedInputStream(in);
            System.out.println("xxxxxxxxx" + in.getClass());
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] data = new byte[1024];
            int n = 0;
            int total = 0;
            while ((n = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, n);
                total += n;
                System.out.println("i:" + (i + 1) + "\tn:" + n + "\tdata:" + Arrays.toString(data));
            }
            System.out.println("i:" + (i + 1) + "\ttotal:" + total);
            System.out.println("i:" + (i + 1) + "\tFINISH");
            outputStream.flush();
            in.close();
            inputStream.close();
            outputStream.close();
            conn.disconnect();
        }
    }
}