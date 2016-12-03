package io.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Property {
    /*
     * �������֪����261��Java�������⡷4.25
     */
    public static void main(String[] args) {
        Property t = new Property();
        try {
//			t.read("info.properties", "name");
            t.read("C:\\Users\\wajian\\Documents\\Test\\dw_app.properties", "appName");
            t.readProperties("C:\\Users\\wajian\\Documents\\Test\\dw_app.properties");
            t.writeProperty("info.properties", "sex", "\u00b6");
            t.read("info.properties", "sex");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String read(String fileName, String key) throws IOException {
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream(fileName));
        String n = props.getProperty(key);
        System.out.println(n);
        return n;
    }

    public void readProperties(String fileName) throws IOException {
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream(fileName));
        Enumeration e = props.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String property = props.getProperty(key);
            System.out.println(key + ":" + property);
        }
    }

    public void writeProperty(String fileName, String key, String value) throws IOException {
        Properties props = new Properties();
        String path = this.getClass().getClassLoader().getResource("").getPath().substring(1);
        path = path + fileName;

        InputStream fis = new FileInputStream(path);
        props.load(fis);
        OutputStream os = new FileOutputStream(path);
        props.setProperty(key, value);
        props.store(os, "updated2 key:" + key);
    }
}
