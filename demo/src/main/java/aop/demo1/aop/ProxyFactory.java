package aop.demo1.aop;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/3/4
 * Time: 12:18
 * A factory for creating Proxy objects.
 */
public class ProxyFactory {
    public static Object getProxy(Object targetObject, List<AbstractHandler> handlers) {
        Object proxyObject;
        if (handlers.size() > 0) {
            proxyObject = targetObject;
            for (AbstractHandler handler : handlers) {
                handler.setTargetObject(proxyObject);
                proxyObject = Proxy.newProxyInstance(targetObject.getClass()
                        .getClassLoader(), targetObject.getClass()
                        .getInterfaces(), handler);
            }
            return proxyObject;
        } else {
            return targetObject;
        }
    }
}
