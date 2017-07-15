package grammar.holder;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 17:32
 */
public class Demo {
    public static void main(String[] args) {
        int a = 1;
        change(a);
        System.out.println("final value of a: " + a);
        System.out.println("-------------------------------------");

        Integer b = 1;
        change2(b);
        System.out.println("final value of b: " + b);
    }

    public static void change(int aa) {
        System.out.println("init value of aa: " + aa);
        aa = 10;
        System.out.println("changed value of aa: " + aa);
    }

    private static void change2(Integer bb) {
        System.out.println("init value of bb: " + bb);
        bb = 10;
        System.out.println("changed value of bb: " + bb);
    }
}
