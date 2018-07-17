package pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnny on 2018/3/18.
 * 被观察者，也就是QQ服务实现 Observable 接口，对其三个方法进行实现
 */
public class QQServer implements Observable {
    //注意到这个List集合的泛型参数为Observer接口，设计原则：面向接口编程而不是面向实现编程
    private List<Observer> list;
    private String message;

    public QQServer() {
        list = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        list.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (!list.isEmpty()) {
            list.remove(o);
        }
    }

    //遍历
    @Override
    public void notifyObserver() {
        for (Observer observer : list) {
            observer.update(message);
        }
    }

    public void setInformation(String s) {
        this.message = s;
        System.out.println("QQ服务更新消息： " + s);
        //消息更新，通知所有观察者
        notifyObserver();
    }
}