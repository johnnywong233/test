package guice.demo2;

import com.google.inject.Inject;

/**
 * Created by johnny on 2016/9/28.
 * test class
 */
public class Client {
    @Inject @MyInterface
    private MyService service;
        //告诉容器，这里的service对象的引用,需要进行注入
    void setService(MyService service) { //这里的方法名字可以任意定义
        this.service = service;
    }
    void myMethod() {
        service.myMethod();
    }
}