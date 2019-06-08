package utils;

import org.testng.annotations.Test;

import java.util.BitSet;

/**
 * Author: Johnny
 * 7 个位运算符，分别是 ~(取反)、&(与)、｜(或)、^(异或)、>>(右移)、<<(左移)、>>>(无符号右移)
 * Date: 2017/2/19
 * Time: 17:38
 */
public class BitUtil {

    //计算n*2
    int mulTwo(int n) {
        return n << 1;
    }

    //除以2，负奇数的运算不可用
    int divTwo(int n) {
        return n >> 1;//除以2
    }

    //计算n*(2^m)，即乘以2的m次方
    int mulTwoPower(int n, int m) {
        return n << m;
    }

    //计算n/(2^m)，即除以2的m次方
    int divTwoPower(int n, int m) {
        return n >> m;
    }

    //判断数值的奇偶性
    boolean isOddNumber(int n) {
        return (n & 1) == 1;
    }

    /**
     * 十进制转十六进制
     */
    public static String decimalToHex(int decimal) {
        StringBuilder hex = new StringBuilder();
        while (decimal != 0) {
            int hexValue = decimal % 16;
            hex.insert(0, toHexChar(hexValue));
            decimal = decimal / 16;
        }
        return hex.toString();
    }

    //将0~15的十进制数转换成0~F的十六进制数
    private static char toHexChar(int hexValue) {
        if (hexValue <= 9 && hexValue >= 0) {
            return (char) (hexValue + '0');
        } else {
            return (char) (hexValue - 10 + 'A');
        }
    }

    @Test
    public void test1() {
        BitSet bm = new BitSet();
        System.out.println(bm.isEmpty() + "--" + bm.size());
        bm.set(0);
        System.out.println(bm.isEmpty() + "--" + bm.size());
        bm.set(1);
        System.out.println(bm.isEmpty() + "--" + bm.size());
        System.out.println(bm.get(65));
        System.out.println(bm.isEmpty() + "--" + bm.size());
        bm.set(65);
        System.out.println(bm.isEmpty() + "--" + bm.size());

        BitSet bm1 = new BitSet(7);
        System.out.println(bm1.isEmpty() + "--" + bm1.size());

        BitSet bm2 = new BitSet(63);
        System.out.println(bm2.isEmpty() + "--" + bm2.size());

        BitSet bm3 = new BitSet(65);
        System.out.println(bm3.isEmpty() + "--" + bm3.size());

        BitSet bm4 = new BitSet(111);
        System.out.println(bm4.isEmpty() + "--" + bm4.size());
    }


    @Test
    public void test() {
        // 1. 获得int型最大值
        System.out.println((1 << 31) - 1);// 2147483647， 由于优先级关系，括号不可省略
        System.out.println(~(1 << 31));// 2147483647

        // 2. 获得int型最小值
        System.out.println(1 << 31);
        System.out.println(1 << -1);

        // 3. 获得long类型的最大值
        System.out.println(((long) 1 << 127) - 1);

        // 4. 乘以2
        System.out.println(10 << 1);

        // 5. 除以2(负奇数的运算不可用)
        System.out.println(10 >> 1);

        // 6. 乘以2的m次方
        System.out.println(10 << 2);

        // 7. 除以2的m次方
        System.out.println(16 >> 2);

        // 9. 不用临时变量交换两个数
        int a = 23, b = 32;
        a ^= b;
        b ^= a;
        a ^= b;

        // 10. 取绝对值（某些机器上，效率比n>0 ? n:-n 高）
        int n = -1;
        System.out.println((n ^ (n >> 31)) - (n >> 31));
        /* n>>31 取得n的符号，若n为正数，n>>31等于0，若n为负数，n>>31等于-1
        若n为正数 n^0-0数不变，若n为负数n^-1 需要计算n和-1的补码，异或后再取补码，
        结果n变号并且绝对值减1，再减去-1就是绝对值 */

        // 11. 取两个数的最大值（某些机器上，效率比a>b ? a:b高）
        System.out.println(b & ((a - b) >> 31) | a & (~(a - b) >> 31));

        // 12. 取两个数的最小值（某些机器上，效率比a>b ? b:a高）
        System.out.println(a & ((a - b) >> 31) | b & (~(a - b) >> 31));

        // 13. 判断符号是否相同(true 表示 x和y有相同的符号， false表示x，y有相反的符号。)
        System.out.println((a ^ b) > 0);

        // 14. 计算2的n次方 n > 0
        System.out.println(2 << (n - 1));

        // 15. 判断一个数n是不是2的幂
        System.out.println((n & (n - 1)) == 0);
        /*如果是2的幂，n一定是100... n-1就是1111....
        所以做与运算结果为0*/

        // 16. 求两个整数的平均值
        System.out.println((a + b) >> 1);

        // 17. 从低位到高位,取n的第m位
        int m = 2;
        System.out.println((n >> (m - 1)) & 1);

        // 18. 从低位到高位.将n的第m位置为1
        System.out.println(n | (1 << (m - 1)));
        /*将1左移m-1位找到第m位，得到000...1...000 n在和这个数做或运算*/

        // 19. 从低位到高位,将n的第m位置为0
        System.out.println(n & ~(0 << (m - 1)));
        /* 将1左移m-1位找到第m位，取反后变成111...0...1111
        n再和这个数做与运算*/
    }


    /**
     * 获取运算数指定位置的值<br>
     * 例如： 0000 1011 获取其第 0 位的值为 1, 第 2 位 的值为 0<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=7)
     * @return 指定位置的值(0 or 1)
     */
    public static byte getBitValue(byte source, int pos) {
        return (byte) ((source >> pos) & 1);
    }

    /**
     * 将运算数指定位置的值置为指定值<br>
     * 例: 0000 1011 需要更新为 0000 1111, 即第 2 位的值需要置为 1<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=7)
     * @param value  只能取值为 0, 或 1, 所有大于0的值作为1处理, 所有小于0的值作为0处理
     * @return 运算后的结果数
     */
    public static byte setBitValue(byte source, int pos, byte value) {
        byte mask = (byte) (1 << pos);
        if (value > 0) {
            source |= mask;
        } else {
            source &= (~mask);
        }
        return source;
    }

    /**
     * 将运算数指定位置取反值<br>
     * 例： 0000 1011 指定第 3 位取反, 结果为 0000 0011; 指定第2位取反, 结果为 0000 1111<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=7)
     * @return 运算后的结果数
     */
    public static byte reverseBitValue(byte source, int pos) {
        byte mask = (byte) (1 << pos);
        return (byte) (source ^ mask);
    }

    /**
     * 检查运算数的指定位置是否为1<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=7)
     * @return true 表示指定位置值为1, false 表示指定位置值为 0
     */
    public static boolean checkBitValue(byte source, int pos) {
        source = (byte) (source >>> pos);
        return (source & 1) == 1;
    }

    public static void main(String[] args) {
        // 取十进制 11 (0000 1011) 
        byte source = 11;
        // 取第2位值并输出, 结果应为 0000 1011
        for (byte i = 7; i >= 0; i--) {
            System.out.printf("%d ", getBitValue(source, i));
        }

        // 将第6位置为1并输出 , 结果为 75 (0100 1011)
        System.out.println("\n" + setBitValue(source, 6, (byte) 1));

        // 将第6位取反并输出, 结果应为75(0100 1011)
        System.out.println(reverseBitValue(source, 6));

        // 检查第6位是否为1，结果应为false
        System.out.println(checkBitValue(source, 6));

        // 输出为1的位, 结果应为 0 1 3
        for (byte i = 0; i < 8; i++) {
            if (checkBitValue(source, i)) {
                System.out.printf("%d ", i);
            }
        }

    }

}

/**
 * 获取一个字符串中用到哪些字符
 */
class WhichChars {

    private BitSet used = new BitSet();

    public WhichChars(String str) {
        for (int i = 0; i < str.length(); i++) {
            used.set(str.charAt(i));  // set bit for char
        }
    }

    @Override
    public String toString() {
        StringBuilder desc = new StringBuilder("[");
        int size = used.size();
        for (int i = 0; i < size; i++) {
            if (used.get(i)) {
                desc.append((char) i);
            }
        }
        return desc + "]";
    }

    public static void main(String args[]) {
        WhichChars w = new WhichChars("How do you do");
        System.out.println(w);
    }
}