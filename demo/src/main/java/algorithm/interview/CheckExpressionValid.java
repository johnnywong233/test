package algorithm.interview;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Johnny
 * Date: 2018/5/18
 * Time: 0:01
 */
public class CheckExpressionValid {
    public static void main(String[] args) throws Exception {
        CheckExpressionValid demo = new CheckExpressionValid();
        String exp = "(1+2)*(3/(5-4))";
        //方法一：使用 JS 引擎或者 nashorn 引擎
        System.out.println(jsCheck(exp));
        //方法二：正则表达式
        System.out.println(regexCheck(exp));
        //方法三：实际上也是面试想要考查的数据结构知识：
        System.out.println(demo.eval(exp));

    }

    //https://zhidao.baidu.com/question/390927694476683085.html
    private static boolean regexCheck(String exp) {
//        String regex = "((([(]?[-]?[0-9]*[.]?[0-9]+)+([\\/\\+\\-\\*])+)+([0-9]*[.]?[0-9]+[)]?)+[\\+\\-\\*\\/]?([0-9]*)*)+";
//        String regex = "((([(]?[-]?[0-9]*[.]?[0-9]+)+([/+\\-*])+)+([0-9]*[.]?[0-9]+[)]?)+[+\\-*/]?([0-9]*)*)+";
        String regex = "^(?!.*[^\\d+\\-*/()])(?!.*\\)\\d)(?!.*[+\\-*/]([+\\-*/]|\\)))(?!.*\\([+*/])(?!.*(\\d|\\))\\()(?=\\d|-|\\()(?=.*(\\d|\\))$)[^()]*(((?'open'\\()[^()]*)+((?'-open'\\))[^()]*)+)*(?(open)(?!))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(exp);


        return Pattern.matches(regex, exp);

    }

    private static boolean jsCheck(String exp) {
        boolean result = false;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");//此处可以选择想要的引擎
        try {
            Object obj = engine.eval(exp);
            System.out.println(obj.toString());
            result = true;
        } catch (ScriptException e) {
            //对错误堆栈不感兴趣
        }
        return result;
    }

    private String eval(String exp) throws Exception {
        List<String> list = infixExpToPostExp(exp);// 转化成后缀表达式
        return doEval(list);// 真正求值
    }

    // 遇到操作符压栈，遇到表达式从后缀表达式中弹出两个数，计算出结果，压入堆栈
    private String doEval(List<String> list) throws Exception {
        Stack<String> stack = new Stack<>();
        String element;
        double n1, n2, result;
        try {
            for (Object aList : list) {
                element = aList.toString();
                if (isOperator(element)) {
                    n1 = Double.parseDouble(stack.pop());
                    n2 = Double.parseDouble(stack.pop());
                    result = doOperate(n2, n1, element);
                    stack.push(result + "");
                } else {
                    stack.push(element);
                }
            }
            return stack.pop();
        } catch (RuntimeException e) {
            throw new Exception(e.getMessage());
        }
    }

    private double doOperate(double n1, double n2, String operator) {
        return switch (operator) {
            case "+" -> n1 + n2;
            case "-" -> n1 - n2;
            case "*" -> n1 * n2;
            default -> n1 / n2;
        };
    }

    private boolean isOperator(String str) {
        return "+".equals(str) || "-".equals(str) || "*".equals(str)
                || "/".equals(str);
    }

    private List<String> infixExpToPostExp(String exp) throws Exception {
        // 将中缀表达式转化成为后缀表达式
        exp = exp + "#";
        List<String> postExp = new ArrayList<>();// 存放转化的后缀表达式的链表
        StringBuilder numBuffer = new StringBuilder();// 用来保存一个数的
        Stack<String> opStack = new Stack<>();// 操作符栈
        char ch;
        String preChar;
        opStack.push("#");
        try {
            for (int i = 0; i < exp.length(); ) {
                ch = exp.charAt(i);
                switch (ch) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        preChar = opStack.peek();
                        // 如果栈里面的操作符优先级比当前的大，则把栈中优先级大的都添加到后缀表达式列表中
                        while (priority(preChar.charAt(0)) >= priority(ch)) {
                            postExp.add(preChar);
                            opStack.pop();
                            preChar = opStack.peek();
                        }
                        opStack.push("" + ch);
                        i++;
                        break;
                    case '(':
                        // 左括号直接压栈
                        opStack.push("" + ch);
                        i++;
                        break;
                    case ')':
                        // 右括号则直接把栈中左括号前面的弹出，并加入后缀表达式链表中
                        String c = opStack.pop();
                        while (c.charAt(0) != '(') {
                            postExp.add(c);
                            c = opStack.pop();
                        }
                        i++;
                        break;
                    // #号，代表表达式结束，可以直接把操作符栈中剩余的操作符全部弹出，并加入后缀表达式链表中
                    case '#':
                        String c1;
                        while (!opStack.isEmpty()) {
                            c1 = opStack.pop();
                            if (c1.charAt(0) != '#') {
                                postExp.add(c1);
                            }
                        }
                        i++;
                        break;
                    // 过滤空白符
                    case ' ':
                    case '\t':
                        i++;
                        break;
                    // 数字则凑成一个整数，加入后缀表达式链表中
                    default:
                        if (Character.isDigit(ch) || ch == '.') {
                            while (Character.isDigit(ch) || ch == '.') {
                                numBuffer.append(ch);
                                ch = exp.charAt(++i);
                            }
                            postExp.add(numBuffer.toString());
                            numBuffer = new StringBuilder();
                        } else {
                            throw new Exception("illegal operator");
                        }
                }
            }
        } catch (RuntimeException e) {
            throw new Exception(e.getMessage());
        }
        return postExp;
    }

    private int priority(char op) throws Exception {
        // 定义优先级
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '(', '#' -> 0;
            default -> throw new Exception("Illegal operator");
        };
    }
}
