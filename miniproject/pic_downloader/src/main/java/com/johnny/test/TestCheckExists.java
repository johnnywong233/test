package com.johnny.test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestCheckExists {
    public static void main(String[] args) {
    	Integer retryTimes = 3;
        for (int i = 0; i < retryTimes; i++)
            try {
                URL url = new URL("https://www.douban.com/photos/photo/2494907038/#image");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(false);

                conn.setRequestMethod("HEAD");
                System.out.println(i + " = " + conn.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
