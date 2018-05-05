package pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Author: Johnny
 * Date: 2018/5/3
 * Time: 22:27
 * 静态内部类 单例模式的升级版，防止反射攻击
 */
public class UpgradeSingleton {
    private static class LazyHolder {
        private static final UpgradeSingleton INSTANCE = new UpgradeSingleton();
    }

    private static boolean flag = false;

    private UpgradeSingleton() {
        synchronized (UpgradeSingleton.class) {
            if (flag == false) {
                flag = !flag;
            } else {
                throw new RuntimeException("单例模式被侵犯！");
            }
        }
    }

    public static UpgradeSingleton getInstance() {
        return UpgradeSingleton.LazyHolder.INSTANCE;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获得构造器
        Constructor con = UpgradeSingleton.class.getDeclaredConstructor();
        //设置为可访问
        con.setAccessible(true);
        //构造两个不同的对象
        UpgradeSingleton singleton1 = (UpgradeSingleton) con.newInstance();
        UpgradeSingleton singleton2 = (UpgradeSingleton) con.newInstance();
        //验证是否是不同对象
        System.out.println(singleton1.equals(singleton2));
    }
}
