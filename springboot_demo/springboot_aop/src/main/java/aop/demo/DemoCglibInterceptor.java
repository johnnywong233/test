package aop.demo;

import aop.pattern.RealSubject;
import aop.pattern.Subject;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 16:10
 */
public class DemoCglibInterceptor implements MethodInterceptor {
    public static void main(String[] args) {
        Subject subject = (Subject) getProxy(RealSubject.class, new DemoCglibInterceptor());
        subject.request();
    }

    private static Object getProxy(Class<?> clz, MethodInterceptor interceptor) {
        Enhancer enhancer = new Enhancer();
        //生成指定类对象的子类，也就是重写类中的业务函数
        enhancer.setSuperclass(clz);
        //回调函数，加入intercept()函数
        enhancer.setCallback(interceptor);
        //创建这个子类对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before...");
        Object result = methodProxy.invokeSuper(obj, args);
        System.out.println("After...");
        return result;
    }
}
