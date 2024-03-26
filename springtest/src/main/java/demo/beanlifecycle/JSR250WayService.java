package demo.beanlifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class JSR250WayService {
    JSR250WayService() {
        System.out.println("JSR250WayService-构造方法");
    }

    @PostConstruct//构造方法执行之后执行
    public void init() {
        System.out.println("JSR250WayService-init()");
    }

    @PreDestroy//销毁之前执行
    public void destroy() {
        System.out.println("JSR250WayService-destroy()");
    }
}
