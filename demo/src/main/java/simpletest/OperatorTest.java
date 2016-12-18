package simpletest;

/**
 * Created by johnny on 2016/9/16.
 * test on > >> and >>>
 */
public class OperatorTest {
    //http://www.jb51.net/article/51262.htm
    public static void main(String[] args) {
        System.out.println("test on>:");
        int a = 1, b = 2;
        System.out.println(a > b);
        System.out.println("\ntest on>>:");
        System.out.println("15 >> 2 = " + (15 >> 2));//right moved part will be discarded
        System.out.println("\ntest on>>>:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j = j + 5) {
                System.out.println("expected: " + j / (int) (Math.pow(2, i)));
                System.out.println("actual: " + (j >>> i));
                //按二进制形式把所有的数字向右移动对应巍峨位数，低位移出（舍弃），高位的空位补零。对于正数来说和带符号右移相同，对于负数来说不同。
            }
        }
    }
}
