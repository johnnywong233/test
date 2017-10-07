package aop.pattern;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:14
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("real subject execute request");
    }

    @Override
    public void hello() {
        System.out.println("hello");
    }
}
