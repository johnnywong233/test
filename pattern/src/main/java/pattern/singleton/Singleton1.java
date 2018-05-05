package pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Author: Johnny
 * Date: 2018/5/3
 * Time: 21:52
 * 静态内部类的方式实现单例模式
 * main方法测试结果表明：通过反射可以打破单例模式只能创建一个对象实例的约束
 */
public class Singleton1 {
    private static class LazyHolder {
        private static final Singleton1 INSTANCE = new Singleton1();
    }

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获得构造器
        Constructor con = Singleton1.class.getDeclaredConstructor();
        //设置为可访问
        con.setAccessible(true);
        //构造两个不同的对象
        Singleton1 singleton1 = (Singleton1) con.newInstance();
        Singleton1 singleton2 = (Singleton1) con.newInstance();
        //验证是否是不同对象
        System.out.println(singleton1.equals(singleton2));
    }
}
