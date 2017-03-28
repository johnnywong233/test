package aop.demo1.bean;

/**
 * Author: Johnny
 * Date: 2017/3/4
 * Time: 12:08
 */
public class CalculatorImpl implements Calculator {
    @Override
    public int calculate(int a, int b) {
        System.out.println("**********Actual Method Execution**********");
        return a / b;
    }
}
