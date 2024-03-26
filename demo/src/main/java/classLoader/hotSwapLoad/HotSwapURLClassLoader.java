package classLoader.hotSwapLoad;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnny on 2016/10/1.
 * 只要功能是重新加载更改过的.class文件，达到热替换的作用
 */
public class HotSwapURLClassLoader extends URLClassLoader {

    //缓存加载class文件的最后最新修改时间
    private static final Map<String, Long> cacheLastModifyTimeMap = new HashMap<>();
    private static final String projectClassPath = "D:\\Java_ex\\test\\target\\classes\\";
    private static HotSwapURLClassLoader hcl = new HotSwapURLClassLoader();

    private HotSwapURLClassLoader() {
        //设置ClassLoader加载的路径
        super(getMyURLs());
    }

    static HotSwapURLClassLoader getClassLoader() {
        return hcl;
    }

    private static URL[] getMyURLs() {
        URL url = null;
        try {
            url = new File(projectClassPath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new URL[]{url};
    }

    /**
     * 重写loadClass，不采用双亲委托机制("java."开头的类还是会由系统默认ClassLoader加载)
     */
    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class clazz;
        //查看HotSwapURLClassLoader实例缓存下，是否已经加载过class
        //不同的HotSwapURLClassLoader实例是不共享缓存的
        clazz = findLoadedClass(name);
        if (clazz != null) {
            if (resolve) {
                resolveClass(clazz);
            }
            //如果class类被修改过，则重新加载
            if (isModify(name)) {
                hcl = new HotSwapURLClassLoader();
                clazz = customLoad(name, hcl);
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
                    if (resolve) {
                        resolveClass(clazz);
                    }
                    return (clazz);
                }
            } catch (ClassNotFoundException e) {
                // Ignore
            }
        }

        return customLoad(name, this);
    }

    public Class load(String name) throws Exception {
        return loadClass(name);
    }

    private Class customLoad(String name, ClassLoader cl) throws ClassNotFoundException {
        return customLoad(name, false, cl);
    }


    private Class customLoad(String name, boolean resolve, ClassLoader cl)
            throws ClassNotFoundException {
        //findClass()调用的是URLClassLoader里面重载了ClassLoader的findClass()方法
        Class clazz = ((HotSwapURLClassLoader) cl).findClass(name);
        if (resolve) {
            ((HotSwapURLClassLoader) cl).resolveClass(clazz);
        }
        //缓存加载class文件的最后修改时间
        long lastModifyTime = getClassLastModifyTime(name);
        cacheLastModifyTimeMap.put(name, lastModifyTime);
        return clazz;
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    /**
     * @param name name of class
     * @return the latest modify time of .class file
     */
    private long getClassLastModifyTime(String name) {
        String path = getClassCompletePath(name);
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException(new FileNotFoundException(name));
        }
        return file.lastModified();
    }

    /**
     * 判断这个文件跟上次比是否修改过
     *
     * @param name name of class
     * @return true if modified, false if not
     */
    private boolean isModify(String name) {
        long lastModify = getClassLastModifyTime(name);
        long previousModifyTime = cacheLastModifyTimeMap.get(name);
        return lastModify > previousModifyTime;
    }

    /**
     * @param name name of class
     * @return full path of .class file
     */
    private String getClassCompletePath(String name) {
        String simpleName = name.substring(name.lastIndexOf(".") + 1);
        String packagePath = "classLoader/hotSwapLoad/";
        return projectClassPath + packagePath + simpleName + ".class";
    }
}