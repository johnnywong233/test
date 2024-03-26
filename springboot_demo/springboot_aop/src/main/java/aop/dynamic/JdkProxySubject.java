package aop.dynamic;

import aop.pattern.RealSubject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:17
 */
public class JdkProxySubject implements InvocationHandler {
    private final RealSubject realSubject;

    JdkProxySubject(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before.");
        Object result;
        try {
            result = method.invoke(realSubject, args);
        } catch (Exception e) {
            System.out.println("ex:" + e.getMessage());
            throw e;
        } finally {
            System.out.println("after.");
        }
        return result;
    }
}
