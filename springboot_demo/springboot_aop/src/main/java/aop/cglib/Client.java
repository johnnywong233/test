package aop.cglib;

import aop.pattern.RealSubject;
import aop.pattern.Subject;
import net.sf.cglib.proxy.Enhancer;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 18:35
 */
public class Client {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealSubject.class);
        enhancer.setCallback(new DemoMethodInterceptor());
        Subject subject = (Subject) enhancer.create();
        subject.hello();
    }
}
