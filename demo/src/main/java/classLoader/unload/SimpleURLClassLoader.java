package classLoader.unload;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by johnny on 2016/10/1.
 */
public class SimpleURLClassLoader extends URLClassLoader {
    //所有的测试的类都在同一个包下
//    public static String packagePath = "classLoader/unload/";

    SimpleURLClassLoader() {
        //设置ClassLoader加载的路径
        super(getMyURLs());
    }

    private static URL[] getMyURLs() {
        URL url = null;
        try {
            String projectClassPath = "D:\\Java_ex\\test\\target\\classes\\";
            url = new File(projectClassPath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new URL[]{url};
    }

    Class load(String name) throws Exception {
        return loadClass(name);
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    /**
     * 重写loadClass，不采用双亲委托机制("java."开头的类还是会由系统默认ClassLoader加载)
     */
    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class clazz;
        //查看HotSwapURLClassLoader实例缓存下，是否已经加载过class
        clazz = findLoadedClass(name);
        if (clazz != null) {
            if (resolve) {
                resolveClass(clazz);
            }
            return (clazz);
        }

        //如果类的包名为"java."开始，则有系统默认加载器AppClassLoader加载
        if (name.startsWith("java.")) {
            try {
                //得到系统默认的加载cl，即AppClassLoader
                ClassLoader system = ClassLoader.getSystemClassLoader();
                clazz = system.loadClass(name);
                if (clazz != null) {
                    if (resolve)
                        resolveClass(clazz);
                    return (clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return customLoad(name, this);
    }

    private Class customLoad(String name, ClassLoader cl) throws ClassNotFoundException {
        return customLoad(name, false, cl);
    }

    private Class customLoad(String name, boolean resolve, ClassLoader cl)
            throws ClassNotFoundException {
        //findClass()调用的是URLClassLoader里面重载了ClassLoader的findClass()方法
        Class clazz = ((SimpleURLClassLoader) cl).findClass(name);
        if (resolve)
            ((SimpleURLClassLoader) cl).resolveClass(clazz);
        return clazz;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

}