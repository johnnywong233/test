package pattern.observer;

/**
 * Created by Johnny on 2018/3/18.
 * 抽象观察者，定义一个update()方法，当被观察者调用notifyObservers()方法时，观察者的update()方法会被回调。
 */
public interface Observer {
    void update(String message);
}