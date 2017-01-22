package demo.beanlifecycle;

public class BeanWayService {
    public void init() {
        System.out.println("BeanWayService-init()");
    }

    public BeanWayService() {
        System.out.println("BeanWayService-constructor");
    }

    public void destroy() {
        System.out.println("BeanWayService-destroy()");
    }
}
