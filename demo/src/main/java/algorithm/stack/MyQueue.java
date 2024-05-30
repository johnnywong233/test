package algorithm.stack;

import java.util.Stack;

/**
 * 用两个栈实现队列
 *
 * @author johnny
 */
public class MyQueue<T> {
    private final Stack<T> stackIn; // 用于入队操作
    private final Stack<T> stackOut; // 用于出队操作

    public MyQueue() {
        stackIn = new Stack<>();
        stackOut = new Stack<>();
    }

    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Peek: " + queue.peek());
        System.out.println("Size: " + queue.size());
    }

    // 入队操作，将元素压入 stackIn 栈
    public void enqueue(T element) {
        stackIn.push(element);
    }

    // 出队操作，先将 stackIn 栈中的元素转移到 stackOut 栈，再从 stackOut 栈中弹出元素
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        if (stackOut.isEmpty()) {
            while (!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
        return stackOut.pop();
    }

    // 获取队头元素，不弹出
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        if (stackOut.isEmpty()) {
            while (!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
        return stackOut.peek();
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return stackIn.isEmpty() && stackOut.isEmpty();
    }

    // 获取队列的大小
    public int size() {
        return stackIn.size() + stackOut.size();
    }
}
