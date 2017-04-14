package grammar.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by wajian on 2016/10/5.
 * https://my.oschina.net/u/1866821/blog/364773
 * http://blog.csdn.net/leon709/article/details/9529307
 * ​cglib实现动态代理构建带参数的代理实例
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CglibProxy implements MethodInterceptor {
    /**
     * 创建代理对象方法
     *
     * @param target    代理对象
     * @param args      对应的构造器参数类型
     *                  例：有构造器如下
     *                  public Person(name,age){...} name为String.class age为int.class
     *                  写入name的类型与age的类型
     *                  <p>
     *                  则：new Class[]{String.class,int.class}
     * @param argsValue 对应的构造器参数值
     *                  例:如此创建对象 new Person("name",23) 用以下方式传入：new Object[]{"name",23}
     * @param <T>       <泛型方法>
     * @return 返回跟代理对象类型
     */
    public <T> T getInstance(T target, Class[] args, Object[] argsValue) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create(args, argsValue);
    }

    /**
     * 创建代理对象方法
     *
     * @param target 代理对象
     * @param <T>    <泛型方法>
     * @return 返回跟代理对象类型
     */
    public <T> T getInstance(T target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();// create proxy instance
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        try {
            System.out.println("Before " + method.getName() + " ..");
            result = methodProxy.invokeSuper(o, objects);
            System.out.println("End " + method.getName() + " ..");
        } catch (Exception e) {
            System.out.println("Error " + method.getName() + " ..");
        }
        return result;
    }

    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        Hello hello = proxy.getInstance(new HelloImpl());
        System.out.println(hello.sayHello("Leon"));
        UserDaoImpl userDao = proxy.getInstance(new UserDaoImpl());
        userDao.login("Leon", "1234");
        System.out.println(userDao.getClass().getSuperclass());//看动态代理实例的父类
    }
}

interface UserDao {
    boolean login(String username, String password);
}

class UserDaoImpl implements UserDao {
    @Override
    public boolean login(String username, String password) {
        String user = "(" + username + "," + password + ")";
        System.out.println(this.getClass().getName() + "-> processing login:"
                + user);
        return true;
    }
}