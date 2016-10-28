package file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by johnny on 2016/9/16.
 * CRUD on properties
 */
public class PropertiesDemo {
    public static void main(String args[]) throws IOException {
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
}