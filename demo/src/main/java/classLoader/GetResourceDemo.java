package classLoader;

import org.testng.annotations.Test;
import simpletest.OOMdemo.Demo1;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by johnny on 2016/9/8.
 * <a href="http://blog.chinaunix.net/uid-21227800-id-65886.html">...</a>
 */
public class GetResourceDemo extends JPanel {
    private static void readTxt(String filePath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void demo() throws MalformedURLException {
        //load image from file, through classLoader.getResource()
        Image image;
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("AStar.png")).getFile());
        System.out.println("get the URI " + file.toURI());

        String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath().substring(1);
        System.out.println("get the path " + path);

//        image = getToolkit().getImage(file.toURL()); //deprecated method: file.toURL()
        image = getToolkit().getImage(file.toURI().toURL());

        ImageIcon ico = new ImageIcon(image);
        System.out.println("OK load image! Description: " + ico.getDescription() + ", IconHeight: " + ico.getIconHeight()
                + ", ImageLoadStatu " + ico.getImageLoadStatus());

        //load properties from file, through classLoader.getResourceAsStream()
        InputStream is = classLoader.getResourceAsStream("widgets.properties");
        if (is == null) {
            System.err.println("Can't load properties file");
            return;
        }

        //create Properties
        Properties p = new Properties();
        try {
            p.load(is);
        } catch (IOException ex) {
            System.err.println("Load failed: " + ex);
            return;
        }
        p.list(System.out);
    }

    /**
     * difference between getResource and class.getClassLoader().getResource
     * 1.前者获取的是当前类加载的路径,如果用此方法读取文件则有两种方法,与相对路径绝对路径非常类似.
     * 2.后者获取的是类加载器的路径,即会到classpath路径下.可以理解当前在 classp/ 目录下,要想访问哪个文件,直接填写路径即可,不用区分相对路径和绝对路径.
     */
    @Test
    public void test() {
        System.out.println("classpath path " + Objects.requireNonNull(Demo1.class.getClassLoader().getResource("")).getPath());
        System.out.println("current class load path " + Objects.requireNonNull(Demo1.class.getResource("")).getPath());
        //methods of getting the src/main/resources directory files
        //method 1: 从classpath路径出发读取
        readTxt(Objects.requireNonNull(Demo1.class.getClassLoader().getResource("test/demo1.txt")).getPath());
        //method 2: 从类加载路径出发, equivalently use absolute directory
        readTxt(Objects.requireNonNull(Demo1.class.getResource("/test/demo1.txt")).getPath());
        //method 3: 从类加载路径出发, equivalently use relative directory
        readTxt(Objects.requireNonNull(Demo1.class.getResource("../../../test/demo1.txt")).getPath());
    }

}
