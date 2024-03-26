package project.netSpider;

import java.util.LinkedList;

public class Queue<T> {
    private final LinkedList<T> queue = new LinkedList<>();

    void enQueue(T t) {
        queue.addLast(t);
    }

    T deQueue() {
        return queue.removeFirst();
    }

    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }

    boolean contains(T t) {
        return queue.contains(t);
    }

    boolean empty() {
        return queue.isEmpty();
    }
}
