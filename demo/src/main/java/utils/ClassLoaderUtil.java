package utils;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 23:54
 */
public class ClassLoaderUtil {
    public static void main(String[] args) {
        System.out.println(printAllClassLoaderJars(ClassLoaderUtil.class.getClassLoader()));
    }

    private static String printAllClassLoaderJars(ClassLoader cl) {
        int i = 0;
        ClassLoader cl0 = cl;
        while (cl0 != null) {
            cl0 = cl0.getParent();
            i++;
        }
        cl0 = cl;

        TreeMap<Integer, ClassLoader> map = new TreeMap<>();
        while (cl0 != null) {
            map.put(i, cl0);
            cl0 = cl0.getParent();
            i--;
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, ClassLoader> entry : map.entrySet()) {
            try {
                sb.append(printClassLoaderJars(entry.getValue(), entry.getKey()));
            } catch (Exception e) {
                sb.delete(0, sb.length() - 1);
                sb.append("catch exception: " + e.getMessage());
                return sb.toString();
            }
        }
        return sb.toString();
    }

    private static String printClassLoaderJars(ClassLoader cl, Integer i) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n");
        sb.append("--" + i + "--" + cl.getClass().getName() + "@" + System.identityHashCode(cl) + "--");
        sb.append("\r\n");
        boolean isUrl = false;
        if (cl instanceof URLClassLoader) {
            Class<?> clazz = cl.getClass();
            while (clazz != URLClassLoader.class && clazz != Object.class) {
                clazz = clazz.getSuperclass();
            }
            if (clazz == URLClassLoader.class) {
                isUrl = true;
                Field f = clazz.getDeclaredField("ucp");
                f.setAccessible(true);
                URLClassLoader ucp = (URLClassLoader) f.get(cl);
                URL[] urls = ucp.getURLs();
                for (URL url : urls) {
                    sb.append(url.toString());
                    sb.append("\r\n");
                }
            }
        }
        if (!isUrl) {
            sb.append("is not URLClassLoader.");
            sb.append("\r\n");
        }
        return sb.toString();
    }

}
