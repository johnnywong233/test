package classLoader.demo1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by johnny on 2016/10/7.
 * use ClassLoader to load a modified class, replace the old one
 * note: better use interface, and the classloader just load class not interface,
 * which make sure there is no error while type casting
 */
public class Main {
    static Target obj = new TargetImpl();

    //http://www.mincoder.com/article/4459.shtml
    public static void main(String[] args) throws Exception {
        String path = "C:\\work\\test\\target\\classes\\classLoader\\demo1\\TargetImpl.class";
        byte[] b = getBytes(path);
        Class<?> c = new DynamicClassLoader().findClass(b);
        obj = (Target) c.getDeclaredConstructor().newInstance();
        System.err.println(obj.name());
        TimeUnit.SECONDS.sleep(2);
    }

    //read file from local
    private static byte[] getBytes(String filename) throws IOException {
        File file = new File(filename);
        long len = file.length();
        byte[] raw = new byte[(int) len];
        FileInputStream fin = new FileInputStream(file);
        fin.read(raw);
        fin.close();
        return raw;
    }
}
