package aop.pattern;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:14
 */
public class Proxy implements Subject {
    private final RealSubject realSubject;

    Proxy(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public void request() {
        System.out.println("before");
        try {
            realSubject.request();
        } catch (Exception e) {
            System.out.println("ex:" + e.getMessage());
            throw e;
        } finally {
            System.out.println("after");
        }
    }

    @Override
    public void hello() {
        realSubject.hello();
    }
}
