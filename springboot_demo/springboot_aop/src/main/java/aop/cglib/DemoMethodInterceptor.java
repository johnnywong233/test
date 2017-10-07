package aop.cglib;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 18:33
 */
public class DemoMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before in cglib");
        Object result;
        try {
            result = proxy.invokeSuper(obj, args);
        } catch (Exception e) {
            System.out.println("get ex:" + e.getMessage());
            throw e;
        } finally {
            System.out.println("after in cglib");
        }
        return result;
    }
}
