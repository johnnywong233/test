package algorithm.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * 约瑟夫环 Josephus 问题
 * 假设有n个人围成一圈，从第一个人开始报数，报到m的人出列，然后从出列的下一个人开始重新报数，直到所有人都出列为止。
 * 问题：给定n个人和数字m，求出最后留下的人的编号。
 *
 * @author johnny
 */
public class Josephus {

    public static void main(String[] args) {
        int n = 7, m = 3;
        System.out.println(josephus(n, m));
        // 编号从1开始
        System.out.println(josephusRecursive(n, m) + 1);
        System.out.println(josephusLoop(n, m) + 1);
    }

    public static int josephus(int n, int m) {
        List<Integer> people = new ArrayList<>();
        // 初始化人员列表
        for (int i = 1; i <= n; i++) {
            people.add(i);
        }
        int index = 0; // 从第一个人开始报数
        while (people.size() > 1) {
            // 计算下一个出列的人的索引
            index = (index + m - 1) % people.size();
            // 移除出列的人
            people.remove(index);
        }
        // 返回最后剩下的人的编号
        return people.getFirst();
    }

    /**
     * 递归解法
     */
    public static int josephusRecursive(int n, int m) {
        if (n == 1) {
            return 0; // 只有一个人时，编号为0
        } else {
            return (josephusRecursive(n - 1, m) + m) % n;
        }
    }

    public static int josephusLoop(int n, int m) {
        int last = 0;
        for (int i = 2; i <= n; i++) {
            last = (last + m) % i;
        }
        return last;
    }

}
