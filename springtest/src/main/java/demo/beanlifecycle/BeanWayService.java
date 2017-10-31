package demo.beanlifecycle;

public class BeanWayService {
    void init() {
        System.out.println("BeanWayService-init()");
    }

    BeanWayService() {
        System.out.println("BeanWayService-constructor");
    }

    void destroy() {
        System.out.println("BeanWayService-destroy()");
    }
}
