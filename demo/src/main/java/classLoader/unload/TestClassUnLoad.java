package classLoader.unload;

/**
 * Created by johnny on 2016/10/1.
 */
public class TestClassUnLoad {
    //http://www.blogjava.net/heavensay/archive/2012/11/07/389685.html
    public static void main(String[] args) throws Exception {
        SimpleURLClassLoader loader = new SimpleURLClassLoader();
        // 用自定义的加载器加载A
        Class<?> clazzA = loader.load("classLoader.unload.Unload");
        Object a = clazzA.getDeclaredConstructor().newInstance();
        // 清除相关引用
        a = null;
        clazzA = null;
        loader = null;
        // 执行一次gc垃圾回收
        System.gc();
        System.out.println("GC over");
    }
}
