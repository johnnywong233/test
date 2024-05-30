package algorithm.stack;

import java.util.Stack;

/**
 * 定义栈的数据结构，并实现一个得到栈的最小元素的min方法，且min、push及pop的时间复杂度都是O(1)
 * @author johnny
 */
public class MinStack {
    private final Stack<Integer> stack;
    private final Stack<Integer> minStack;

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println("Minimum element in the stack: " + minStack.getMin()); // 返回 -3
        minStack.pop();
        System.out.println("Top element in the stack: " + minStack.top()); // 返回 0
        System.out.println("Minimum element in the stack: " + minStack.getMin()); // 返回 -2
    }

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int x) {
        stack.push(x);
        if (minStack.isEmpty() || x <= minStack.peek()) {
            minStack.push(x);
        }
    }

    public void pop() {
        if (stack.isEmpty()) {
            return;
        }
        int popped = stack.pop();
        if (popped == minStack.peek()) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
