package com.johnny.common.utils;

import com.johnny.common.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLUtils {
    public static String charset = "utf-8";

    public static String readSource(String url) {
        StringBuilder sb = new StringBuilder();
        try {
            URL u = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = sb.toString();
        if (result.trim().length() == 0) {
            Console.print("源码获取失败：" + url);
            return result;
        }
        String charsetCheck;

        if (result.contains("gbk")) {
            charsetCheck = "gbk";
        } else if (result.contains("gb2312")) {
            charsetCheck = "gb2312";
        } else {
            charsetCheck = "utf-8";
        }
        if (!charsetCheck.equals(charset)) {
            Console.print("字符集：" + charset + " -> " + charsetCheck);
            charset = charsetCheck;
            return readSource(url);
        }
        return result;
    }

    public static boolean exists(String url) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        HttpURLConnection.setFollowRedirects(true);
        conn.setInstanceFollowRedirects(true);

        conn.setRequestMethod("HEAD");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");

        conn.setRequestProperty("referer", "https://www.douban.com/");

        System.out.println("ResponseCode:" + conn.getResponseCode());
        return conn.getResponseCode() == 200;
    }

    public static void main(String[] args) throws IOException {
        try {
            System.out.println(readSource("http://www.douban.com/photos/album/51681522/"));
            System.out.println(exists("https://img1.doubanio.com/view/photo/raw/public/p2321685527.jpg"));

            System.out.println(URLUtils.exists("https://www.douban.com/photos/photo/2494849795/#image"));
            System.out.println("==========");
            System.out.println(URLUtils.exists("https://www.douban.com/photos/photo/2494849795"));
            System.out.println("==========");
            System.out.println(URLUtils.exists("https://www.douban.com/photos/photo/2494849795/"));
            System.out.println("==========");
            System.out.println(URLUtils.exists("https://www.douban.com/photos/photo/2494849795/#6"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}