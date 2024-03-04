package algorithm;

import java.util.Random;

/**
 * Created by johnny on 2016/8/17.
 *
 */
public class GeneratePassword {

    /**
     * generate random password
     *
     * @param len length of generated password
     * @return password string
     */
    public static String genRandomNum(int len) {
        // 26*2 letter and 10 digits, equals 62
        final int maxNum = 62;
        int i; //generated password
        int count = 0; //length
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder pwd = new StringBuilder();
        Random r = new Random();
        while (count < len) {
            //use abs to avoid negative number
            i = Math.abs(r.nextInt(maxNum));
            pwd.append(str[i]);
            count++;
        }
        return pwd.toString();
    }

    //http://www.jb51.net/article/78816.htm
    public static void main(String[] args) {
        System.out.println(genRandomNum(16));
    }
}
