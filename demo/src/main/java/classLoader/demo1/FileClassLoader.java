package classLoader.demo1;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by johnny on 2016/10/2.
 * try to use some other's class file
 */
public class FileClassLoader extends ClassLoader {
    private static final String DRIVER = "D:\\Java_ex\\test\\target\\classes\\pic\\";
    private static final String FILE_TYPE = ".class";

    //http://bbs.csdn.net/topics/210030578
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        FileClassLoader loader = new FileClassLoader();
        Class<?> objClass = loader.loadClass("classLoader.demo1.Hello", true);
        Object obj = objClass.getDeclaredConstructor().newInstance();
        System.out.println(objClass.getName());
        System.out.println(objClass.getClassLoader().getClass().getName());
        System.out.println(((Hello) obj).getInfo());
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] data = loadClassData(name);
        return defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        FileInputStream fis;
        byte[] data = null;
        try {
            fis = new FileInputStream(DRIVER + name + FILE_TYPE);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch;
            while ((ch = fis.read()) != -1) {
                baos.write(ch);
            }
            data = baos.toByteArray();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}