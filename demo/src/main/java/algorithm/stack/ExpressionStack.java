package algorithm.stack;

import java.util.Stack;

/**
 * Author: Johnny
 * Date: 2017/10/31
 * Time: 21:42
 *
 */
public class ExpressionStack {

    private final String testString;
    private final Stack<Character> stack;

    private ExpressionStack(String testString) {
        this.testString = testString;
        this.stack = new Stack<>();
    }

    /**
     * 后缀表达式又叫做逆波兰表达式, 本方法实现中缀表达式转换为后缀表达式
     * 判断是否入栈出栈的步骤：
     * 数字时，加入后缀表达式；若为运算符：
     * a. 若为最高级的运算符，入栈；
     * b. 若为 '('，入栈；
     * c. 若为 ')'，则依次把栈中的的运算符加入后缀表达式中，直到出现'('，从栈中删除'(' ；
     * d. 若为不是最高级的运算符，则将从栈顶到第一个优先级不大于（小于，低于或等于）它的运算符
     * （或 '(',但优先满足前一个条件）之间的运算符加入后缀表达式中，该运算符再入栈；
     */
    private void middle2right() {
        for (int i = 0; i < testString.length(); i++) {
            char c = testString.charAt(i);
            if (c == '+' || c == '-') {
                if (stack.isEmpty() || stack.peek() == '(') {
                    stack.push(c);
                } else {
                    while (!stack.isEmpty() && (stack.peek() == '*' || stack.peek() == '/' || stack.peek() == '+' || stack.peek() == '-')) {
                        System.out.print(stack.pop());
                    }
                    stack.push(c);
                }
            } else if (c == '*' || c == '/') {
                if (stack.isEmpty() || stack.peek() == '+' || stack.peek() == '-' || stack.peek() == '(') {
                    stack.push(c);
                } else {
                    while (!stack.isEmpty() && (stack.peek() == '/' || stack.peek() == '*')) {
                        System.out.print(stack.pop());
                    }
                    stack.push(c);
                }
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                char temp;
                while ((temp = stack.pop()) != '(') {
                    System.out.print(temp);
                }
            } else {
                System.out.print(c);
            }
        }
        if (!stack.isEmpty()) {
            while (!stack.isEmpty()) {
                System.out.print(stack.pop());
            }
        }
    }

    public static void main(String[] args) {
        ExpressionStack test = new ExpressionStack("A+B*(C-D)/E+F/H");
        test.middle2right();
    }
}
