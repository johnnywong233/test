package project.netSpider;

import java.util.LinkedList;

/**
 * Created by johnny on 2016/9/16.
 * 数据结构队列
 */
public class Queue<T> {
    private LinkedList<T> queue= new LinkedList<>();

    void enQueue(T t)
    {
        queue.addLast(t);
    }

    T deQueue()
    {
        return queue.removeFirst();
    }

    public boolean isQueueEmpty()
    {
        return queue.isEmpty();
    }

    boolean contians(T t)
    {
        return queue.contains(t);
    }

    boolean empty()
    {
        return queue.isEmpty();
    }
}
