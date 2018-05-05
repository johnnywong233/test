package pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Author: Johnny
 * Date: 2018/5/3
 * Time: 21:56
 * 双重检查锁机制实现单例模式，不要忘记关键字 volatile，防止指令重排的影响
 * main方法测试结果表明：通过反射可以打破单例模式只能创建一个对象实例的约束
 */
public class Singleton2 {
    private Singleton2() {
    }  //私有构造函数

    private volatile static Singleton2 instance = null;  //单例对象

    //静态工厂方法
    public static Singleton2 getInstance() {
        if (instance == null) {      //双重检测机制
            synchronized (Singleton2.class) {  //同步锁
                if (instance == null) {     //双重检测机制
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获得构造器
        Constructor con = Singleton2.class.getDeclaredConstructor();
        //设置为可访问
        con.setAccessible(true);
        //构造两个不同的对象
        Singleton2 singleton1 = (Singleton2) con.newInstance();
        Singleton2 singleton2 = (Singleton2) con.newInstance();
        //验证是否是不同对象
        System.out.println(singleton1.equals(singleton2));
    }
}
