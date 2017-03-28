package aop.demo1.aop;

import java.lang.reflect.Method;

/**
 * Author: Johnny
 * Date: 2017/3/4
 * Time: 12:17
 * The Class BeforeHandler provides a template for the before execution
 */
public abstract class BeforeHandler extends AbstractHandler {
    /**
     * Handles before execution of actual method.
     */
    public abstract void handleBefore(Object proxy, Method method, Object[] args);

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        handleBefore(proxy, method, args);
        return method.invoke(getTargetObject(), args);
    }
}
