package simpletest;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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

    private static void loadFromXML(){
        File file = new File("test.xml");
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties p = new Properties();
        try {
            p.loadFromXML(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.list(System.out);
    }

}
