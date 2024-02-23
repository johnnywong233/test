package grammar.delegate;

import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;

@NoArgsConstructor
public class Delegator implements InvocationHandler {
    protected Object objOrigin = null; //原始对象
    protected Object objProxy = null; //代理对象

    public Delegator(Object origin) {
        this.createProxy(origin);
    }

    protected void createProxy(Object origin) {
        objOrigin = origin;
        // 下面语句中origin.getClass().getClassLoader()为加载器，origin.getClass().getInterfaces()为接口集
        objProxy = Proxy.newProxyInstance(origin.getClass().getClassLoader(), origin.getClass().getInterfaces(), this); //委托
    }

    protected Object invokeSuper(Method method, Object[] args) throws Throwable {
        return method.invoke(objOrigin, args);
    }
    //--------------实现InvocationHandler接口，要求覆盖------------
    //下面实现的方法是当委托的类调用toString()方法时，操作其他方法而不是该类默认的toString()，这个类的其他方法则不会。

    @Override
    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
        // 缺省实现：委托给obj_origin完成对应的操作
        if ("toString".equals(method.getName())) { //对其做额外处理
            return this.invokeSuper(method, args) + "$Proxy";
        } else { //注意，调用原始对象的方法，而不是代理的（obj==obj_proxy）
            return this.invokeSuper(method, args);
        }
    }
}
