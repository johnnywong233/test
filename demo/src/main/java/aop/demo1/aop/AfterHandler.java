package aop.demo1.aop;

import java.lang.reflect.Method;

/**
 * Author: Johnny
 * Date: 2017/3/4
 * Time: 12:16
 * The Class AfterHandler provides a template for After concern.
 */
public abstract class  AfterHandler extends AbstractHandler {
    /**
     * Handles after the execution of method.
     */
    public abstract void handleAfter(Object proxy, Method method, Object[] args);

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(getTargetObject(), args);
        handleAfter(proxy, method, args);
        return result;
    }
}
