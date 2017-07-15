package grammar.holder;

import javax.xml.ws.Holder;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 17:43
 */
public class HolderDemo3 {

    public static void main(String[] args) {
        int a = 1;
        Integer b = 11;
        System.out.println("初始状态--> " + "a: " + a + "    b: " + b);

        add(a, b);
        System.out.println("方法一    --> " + "a: " + a + "    b: " + b);

        add2(a, b);
        System.out.println("方法二    --> " + "a: " + a + "    b: " + b);

        Holder<Integer> holder = new Holder<>(b);
        add3(a, holder);
        System.out.println("方法三    --> " + "a: " + a + "    b: " + holder.value);
    }

    public static void add(int aa, Integer bb) {
        aa = 2;
        bb = 22;
    }

    //leave it be: unnecessary boxing
    private static void add2(int aa, Integer bb) {
        aa = new Integer(2);
        bb = new Integer(22);
    }

    private static void add3(int aa, Holder<Integer> bb) {
        aa = 2;
        bb.value = 22;
    }
}
