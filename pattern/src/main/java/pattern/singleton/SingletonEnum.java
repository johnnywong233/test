package pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Author: Johnny
 * Date: 2018/5/3
 * Time: 21:59
 * 枚举实现单例模式，最简单，可以防止反射重复构造对象实例，保证线程安全；
 * 缺点：其单例对象不是懒加载，而是在枚举类被加载的时候进行初始化的。
 */
public enum SingletonEnum {
    //
    INSTANCE;

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获得构造器
        Constructor con = SingletonEnum.class.getDeclaredConstructor();
        //设置为可访问
        con.setAccessible(true);
        //构造两个不同的对象
        SingletonEnum singleton1 = (SingletonEnum) con.newInstance();
        SingletonEnum singleton2 = (SingletonEnum) con.newInstance();
        //验证是否是不同对象
        System.out.println(singleton1.equals(singleton2));
    }
}
