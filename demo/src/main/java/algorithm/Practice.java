package algorithm;

/**
 * Author: Johnny
 * Date: 2017/2/3
 * Time: 18:38
 */
public class Practice {

    /**
     * 吸血鬼数字是指位数为偶数的数字，可以由一对数字相乘而得到，而这对数字各包含乘积的一半位数的数字，
     * 其中从最初的数字中选取的数字可以任意排序。以两个0结尾的数字是不允许的，例如，下列数字都是“吸血鬼”数字：
     * 1260=21*60,1827=21*87. 找出4位数的所有吸血鬼数字。
     */
    private static void fun() {
        int x = 1000;
        while (x < 10000) {
            int d, c, b, a;
            a = x / 1000;//千分
            b = (x / 100) % 10;//百分位
            c = (x / 10) % 10;//十分位
            d = x % 10;//个位
            if (c != 0 || d != 0) {
                if (x == (a * 10 + b) * (c * 10 + d)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (a * 10 + b) * (d * 10 + c)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (b * 10 + a) * (c * 10 + d)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (b * 10 + a) * (d * 10 + c)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (a * 10 + c) * (b * 10 + d)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (a * 10 + c) * (d * 10 + b)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (c * 10 + a) * (b * 10 + d)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (c * 10 + a) * (d * 10 + b)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (a * 10 + d) * (c * 10 + b)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (a * 10 + d) * (b * 10 + c)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (d * 10 + a) * (c * 10 + b)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
                if (x == (d * 10 + a) * (b * 10 + c)) {
                    System.out.println(x);
                    x++;
                    continue;
                }
            }
            x++;
        }
    }

    //http://www.bianceng.cn/Programming/Java/201608/50343.htm
    public static void main(String[] args) {
        fun();
    }
}
