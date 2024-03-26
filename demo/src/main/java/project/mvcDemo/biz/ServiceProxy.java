package project.mvcDemo.biz;

import project.mvcDemo.util.DbSession;
import project.mvcDemo.util.DbSessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ServiceProxy implements InvocationHandler {
    private final Object target;

    public ServiceProxy(Object target) {
        this.target = target;
    }

    public static Object getProxyInstance(Object target) {
        Class<?> clazz = target.getClass();

        return Proxy.newProxyInstance(clazz.getClassLoader(),
                clazz.getInterfaces(), new ServiceProxy(target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object retValue = null;
        DbSession session = DbSessionFactory.openSession();
        boolean isTxNeeded = !method.getName().startsWith("get");
        try {
            if (isTxNeeded) {
                session.beginTx();
            }
            retValue = method.invoke(target, args);
            if (isTxNeeded) {
                session.commitTx();
            }
        } finally {
            DbSessionFactory.closeSession();
        }
        return retValue;
    }
}
