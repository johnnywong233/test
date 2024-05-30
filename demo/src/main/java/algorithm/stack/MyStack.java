package algorithm.stack;

import java.util.Stack;

/**
 * @author johnny
 */
public class MyStack {

    public static void main(String[] args) {
        int[] pushed = {1, 2, 3, 4, 5};
        int[] popped1 = {4, 3, 5, 1, 2};
        int[] popped2 = {4, 5, 3, 2, 1};
        System.out.println("Is popped1 a valid pop sequence? " + validateStackSequences(pushed, popped1)); // false
        System.out.println("Is popped2 a valid pop sequence? " + validateStackSequences(pushed, popped2)); // true
    }

    /**
     * pushed表示栈元素的入栈次序，popped表示栈元素的出栈次序，返回true/false表示出栈顺序是否合理
     */
    public static boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int j = 0;
        for (int num : pushed) {
            stack.push(num);
            while (!stack.isEmpty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }

}
