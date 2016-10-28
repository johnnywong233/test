package simpletest;

import java.util.concurrent.PriorityBlockingQueue;

import org.w3c.dom.events.Event;

public class EventTest implements Comparable<Event> {
    //TODO
    private int thread;
    private int priority;

    public EventTest(int thread, int priority) {
        this.thread = thread;
        this.priority = priority;
    }

    public int getThread() {
        return thread;
    }

    public int getPriority() {
        return priority;
    }


    @Override
    public int compareTo(Event o) {
        if (this.priority > e.getPriority()) {
            return -1;
        } else if (this.priority < e.getPriority()) {
            return 1;
        } else {
            return 0;
        }

    }

    public class Task implements Runnable {
        private int id;
        private PriorityBlockingQueue<Event> queue;

        public Task(int id, PriorityBlockingQueue<Event> queue) {
            this.id = id;
            this.queue = queue;
        }


        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                Event event = new Event(id, i);
                queue.add(event);
            }
        }
    }
}



