package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnny on 2016/8/30.
 */
public class LinkedReverse {
    /**
     * Java实现单链表翻转
     */
    //http://www.phpxs.com/code/1002195/
    public static void main(String[] args) {
        N n = new N();
        n.name = "A";
        N n1 = new N();
        n1.name = "B";
        N n2 = new N();
        n2.name = "C";
        N n3 = new N();
        n3.name = "D";
        n1.nextN = n2;
        n.nextN = n1;
        n2.nextN = n3;
        N old = n;
        while (old != null) {
            System.out.println(old.name);
            old = old.nextN;
        }

        System.out.println("链表翻转1");
        N new1 = reverseOne(n);
        while (new1 != null) {
            System.out.println(new1.name);
            new1 = new1.nextN;
        }

        /*
        System.out.println("链表翻转2");
        N new2 = reverseTwo(n, null);
        while (new2 != null) {
            System.out.println(new2.name);
            new2 = new2.nextN;
        }

        System.out.println("链表翻转3");
        N new3 = reverseThree(n);
        while (new3 != null) {
            System.out.println(new3.name);
            new3 = new3.nextN;
        }
        */
    }

    //采用交换前后值
    private static N reverseOne(N n) {
        if (n != null) {
            N preN = n; //前一个节点
            N curN = n.nextN; //当前节点
            N nextN;   //后一个节点
            while (null != curN) {
                nextN = curN.nextN;
                curN.nextN = preN;
                preN = curN;
                curN = nextN;
            }
            n.nextN = null;
            n = preN;
            return n;
        }
        return null;
    }

    //采用递归实现
    private static N reverseTwo(N n, N newN) {
        // 采用递归 返回 返回条件是最后一个几点nextN为空
        if (n == null) {
            return newN;
        }
        N nextN = n.nextN;
        n.nextN = newN;
        return reverseTwo(nextN, n);
    }

    //the worst method
    public static N reverseThree(N n) {
        if (n == null) {
            return null;
        }
        // 定义一个集合 ，放在集合里面在单个反向指回
        List<N> nList = new ArrayList<N>();
        N p = n;
        while (p != null) {
            N node = new N();// 当前节点
            node.name = p.name;
            nList.add(node);
            p = p.nextN;
        }
        // 在返现输出节点
        n = null;
        for (N rn : nList) {
            if (n != null) {
                // 如果n不为空时
                rn.nextN = n;
            }
            n = rn;
        }
        return n;
    }
}

//define a node
class N {
    public String name;
    N nextN;
}