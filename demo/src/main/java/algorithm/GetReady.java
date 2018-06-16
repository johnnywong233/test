package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Author: Johnny
 * Date: 2018/4/30
 * Time: 21:01
 */
public class GetReady {

    private String eBook;
    public String geteBook() {
        return eBook;
    }
    public void seteBook(String eBook) {
        this.eBook = eBook;
    }

    private int getMaxNum(int[] nums) {
        int max = 0;
        return max;
    }

    public static void main(String[] args) {
        new GetReady().calculate(new StringBuffer(), 1, 0, 0, 1);

        //解法2
        System.out.println("method 2: ");
        rnwrap(100);

    }

    /**
     * 在1，2，…，9（顺序不变）数字之间插入+或-或什么都不插入，使得计算结果总是100，并输出所有的可能性。
     */
    private void calculate(StringBuffer sb, int i, int r, int n, int f) {
        sb.append(i);
        n = n * 10 + i;
        if (i == 9) {
            if (r + f * n == 100) {
                System.out.print(sb);
                System.out.println("=100");
            }
            sb.deleteCharAt(sb.length() - 1);
            return;
        }
        // 1:跟""
        calculate(sb, i + 1, r, n, f);
        if (sb.charAt(sb.length() - 1) == '+' || sb.charAt(sb.length() - 1) == '-')
            return;
        r += f * n;
        // 2:跟"+"
        sb.append('+');
        calculate(sb, i + 1, r, 0, 1);
        // 3:跟"-"
        sb.setCharAt(sb.length() - 1, '-');
        calculate(sb, i + 1, r, 0, -1);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
    }


    public static Stack<String> rn(int n) {
        Stack<String> stack = new Stack<>();
        Stack<String> news = new Stack<>();
        for (int i = 9; i >= 1; i--) {
            stack.add(i + "");
            if (i != 1) {
                stack.add("");
            }
        }
        for (int i = 0; i < 8; i++) {
            String c1 = stack.pop();
            String opt = stack.pop();
            String c2 = stack.pop();
            if (n % 3 == 0) {
                Integer temp = Integer.valueOf(c1) * 10 + Integer.valueOf(c2);
                stack.push(temp.toString());
            } else {
                news.push(c1);
                if (n % 3 == 1) {
                    news.push("+");
                } else {
                    news.push("-");
                }
                stack.push(c2);
            }
            n = n / 3;
        }
        if (!stack.isEmpty()) {
            news.push(stack.pop());
        }
        return news;
    }

    public static List<String> rnwrap(int total) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.pow(3, 8); i++) {
            Stack<String> st = rn(i);
            int sum = 0;
            int opt = 0;

            Stack<String> nst = new Stack<>();
            while (!st.isEmpty()) {
                nst.push(st.pop());
            }
            StringBuffer sb = new StringBuffer("");
            while (!nst.isEmpty()) {
                String s1 = nst.pop();
                sb.append(s1);
                if (s1.equals("+")) {
                    opt = 1;
                } else if (s1.equals("-")) {
                    opt = 2;
                } else {
                    if (opt == 1) {
                        sum = sum + Integer.valueOf(s1);
                    } else if (opt == 2) {
                        sum = sum - Integer.valueOf(s1);
                    } else {
                        sum = sum + Integer.valueOf(s1);
                    }
                    opt = 0;
                }
            }
            if (sum == total) {
                System.out.println(sb);
            }
        }
        return result;
    }



}
