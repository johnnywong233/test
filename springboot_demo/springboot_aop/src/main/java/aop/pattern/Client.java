package aop.pattern;


/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:12
 */
public class Client {
    public static void main(String[] args) {
        Subject subject = new Proxy(new RealSubject());
        subject.request();
    }
}