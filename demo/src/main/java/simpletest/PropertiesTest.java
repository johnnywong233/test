package simpletest;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesTest {

    @Test
    public void test() throws Exception {
        load();
        loadFromXML();
    }

    private static void load() {
    /*中文会出现乱码 */
        File file = new File("test.properties");
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.list(System.out);
    }

    private static void loadFromXML() {
        File file = new File("test.xml");
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties p = new Properties();
        try {
            if (in != null) {
                p.loadFromXML(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.list(System.out);
    }

    @Test
    public static void order() {
        File file = new File("C:\\work\\test\\src\\test\\resources\\LMD.txt");
        Properties prop = new Properties();
        prop.put("a", "aaa");
        prop.put("b", "bbb");

        //java.lang.Integer cannot be cast to java.lang.String
//		prop.put("b", new Integer(4));

        try {
            //can only save the second comment
            prop.store(new FileOutputStream(file), "comment for aaa");
            prop.store(new FileOutputStream(file), "comment for bbb");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file1 = new File("C:\\work\\test\\src\\test\\resources\\already.txt");
        Properties prop1 = new Properties();
        try {
            prop1.load(new FileInputStream(file1));
            prop1.put("ADDED_ONE", "shit");
            prop1.store(new FileOutputStream(file1), "can add a new property work?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void crud() throws IOException {
        Properties prop = new Properties();
        OutputStream out = new FileOutputStream("D:\\Java_ex\\test\\src\\test\\resources\\test.properties");

        //Create: read the file first, add your key-value, then save
        Map<String, Object> toSaveMap = new HashMap<>();
        Set<Object> keys = prop.keySet();
        for (Object key1 : keys) {
            String key = (String) key1;
            Object value = prop.get(key);
            toSaveMap.put(key, value);
        }

        toSaveMap.put("name", "swan");
        toSaveMap.put("age", "25");
        prop.putAll(toSaveMap);
        prop.store(out, "==== after add ====");

        //Update
        prop.clear();
        toSaveMap.put("name", "johnny");
        toSaveMap.put("age", "26");
        prop.putAll(toSaveMap);
        prop.store(out, "==== after modify ====");

        //Delete: find the key you want to delete
        prop.clear();
        toSaveMap.remove("name");
        prop.putAll(toSaveMap);
        prop.store(out, "==== after remove ====");

        //Retrieve
        InputStream in = new FileInputStream("D:\\Java_ex\\test\\src\\test\\resources\\test.properties");
        prop.load(in);
        System.out.println("name: " + prop.get("name"));
        System.out.println("age: " + prop.get("age"));
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
        Enumeration<?> e = props.propertyNames();
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

    @Test
    public static void test1() {
        PropertiesTest t = new PropertiesTest();
        try {
            t.readProperties("C:\\Users\\wajian\\Documents\\Test\\dw_app.properties");
            t.writeProperty("info.properties", "sex", "\u00b6");
            t.read("info.properties", "sex");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
