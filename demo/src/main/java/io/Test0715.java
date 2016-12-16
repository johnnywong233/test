package io;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Test0715 {
    /*
     * URLͨ��proxy�������Internet��Դ
     */
    public static void main(String args[]) {
        String sURL = "http://java.sun.com/index.html";
        Properties prop = System.getProperties();
        prop.put("http.proxyHost", "172.16.0.10");
        prop.put("http.proxyPort", "11080");
        try {
            URL url = new URL(sURL);
            URLConnection conn = url.openConnection();
            conn.connect();
            System.out.println(conn.getContentLength());
            InputStream is = conn.getInputStream();
            System.out.println("ic:" + is.read());
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
