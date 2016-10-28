package classLoader.demo1;

/**
 * Created by wajian on 2016/10/7.
 */
public class DynamicClassLoader extends ClassLoader{
    public Class<?> findClass(byte[] b) throws ClassNotFoundException {
        return defineClass(null, b, 0, b.length);
    }
}
