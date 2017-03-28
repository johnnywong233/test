package aop.demo1.aop;

import java.lang.reflect.InvocationHandler;

/**
 * Author: Johnny
 * Date: 2017/3/4
 * Time: 12:14
 * The Class AbstractHandler provides a simple wrapper for our own aop.
 */
public abstract class AbstractHandler implements InvocationHandler {

    private Object targetObject;

    void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    Object getTargetObject() {
        return targetObject;
    }

}
