package classLoader.demo1;

/**
 * Created by johnny on 2016/10/7.
 */
public class DynamicClassLoader extends ClassLoader {
    public Class<?> findClass(byte[] b) {
        return defineClass(null, b, 0, b.length);
    }
}
