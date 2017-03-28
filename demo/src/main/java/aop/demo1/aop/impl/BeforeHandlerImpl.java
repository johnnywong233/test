package aop.demo1.aop.impl;

import aop.demo1.aop.BeforeHandler;

import java.lang.reflect.Method;

/**
 * Author: Johnny
 * Date: 2017/3/4
 * Time: 12:20
 * Description: The Class BeforeHandlerImpl provides implementation before actual execution of method.
 */
public class BeforeHandlerImpl extends BeforeHandler {
    @Override
    public void handleBefore(Object proxy, Method method, Object[] args) {
        //Provide your own cross cutting concern
        System.out.println("Handling before actual method execution ........");
    }
}
