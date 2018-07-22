package aop.demo1.aop.impl;

import aop.demo1.aop.AbstractAfterHandler;

import java.lang.reflect.Method;

/**
 * Author: Johnny
 * Date: 2017/3/4
 * Time: 12:19
 * Description: The Class AfterHandlerImpl provides an implementation of business logic which will be executed after the actual method execution.
 */
public class AfterHandlerImpl extends AbstractAfterHandler {
    @Override
    public void handleAfter(Object proxy, Method method, Object[] args) {
        //Provide your own cross cutting concern
        System.out.println("Handling after actual method execution ........");
    }
}
