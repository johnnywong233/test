package algorithm.interview;

/**
 * 大数相加
 *
 * @author johnny
 */
public class BigNumAdd {

    private String add(String n1, String n2) {
        // 1.把两个大整数用数组逆序存储，数组长度等于较大整数位数+1
        int maxLength = Math.max(n1.length(), n2.length());
        int[] arrayA = new int[maxLength + 1];
        for (int i = 0; i < n1.length(); i++) {
            arrayA[i] = n1.charAt(n1.length() - 1 - i) - '0';
        }
        int[] arrayB = new int[maxLength + 1];
        for (int i = 0; i < n2.length(); i++) {
            arrayB[i] = n2.charAt(n2.length() - 1 - i) - '0';
        }
        // 2.构建result数组，数组长度等于较大整数位数+1
        int[] result = new int[maxLength + 1];
        // 3.遍历数组，按位相加
        for (int i = 0; i < result.length; i++) {
            int temp = result[i];
            temp += arrayA[i];
            temp += arrayB[i];
            //判断是否进位
            if (temp >= 10) {
                temp = temp - 10;
                result[i + 1] = 1;
            }
            result[i] = temp;
        }
        StringBuilder sb = new StringBuilder();
        // 是否找到大整数的最高有效位
        boolean findFirst = false;
        for (int i = result.length - 1; i >= 0; i--) {
            if (!findFirst) {
                if (result[i] == 0) {
                    continue;
                }
                findFirst = true;
            }
            sb.append(result[i]);
        }
        return sb.toString();
    }
}
