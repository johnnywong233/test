package aop.dynamic;

import aop.pattern.RealSubject;
import aop.pattern.Subject;

import java.lang.reflect.Proxy;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 17:17
 */
public class Client {
    public static void main(String[] args){
        //将在本地创建一个编译后的class文件com.sun.proxy.$Proxy0.class
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Subject subject = (Subject) Proxy.newProxyInstance(Client.class.getClassLoader(),new Class[]{Subject.class},new JdkProxySubject(new RealSubject()));
        subject.hello();
    }
}
