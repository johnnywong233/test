package pattern.observer;

//import java.util.Observer;
/**
 * Created by Johnny on 2018/3/18.
 * 抽象被观察者接口，声明添加、删除、通知观察者方法
 */
public interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObserver();
}