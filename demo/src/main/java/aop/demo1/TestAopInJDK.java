package aop.demo1;

import aop.demo1.aop.AbstractHandler;
import aop.demo1.aop.AfterHandler;
import aop.demo1.aop.BeforeHandler;
import aop.demo1.aop.ProxyFactory;
import aop.demo1.aop.impl.AfterHandlerImpl;
import aop.demo1.aop.impl.BeforeHandlerImpl;
import aop.demo1.bean.Calculator;
import aop.demo1.bean.CalculatorImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/3/4
 * Time: 12:24
 * The Test class to verify our own aop using JDK proxy.
 */
public class TestAopInJDK {
    //https://github.com/debjava/aopusingjdkdynamicproxy
    public static void main(String[] args) {
        CalculatorImpl calcImpl = new CalculatorImpl();
        BeforeHandler before = new BeforeHandlerImpl();
        AfterHandler after = new AfterHandlerImpl();
        List<AbstractHandler> handlers = new ArrayList<>();
        handlers.add(before);
        handlers.add(after);
        Calculator proxy = (Calculator) ProxyFactory.getProxy(calcImpl,
                handlers);
        int result = proxy.calculate(20, 10);
        System.out.println("Final Result :::" + result);
    }
}
