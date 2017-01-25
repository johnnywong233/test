package useless;

import java.util.Random;

/**
 * Author: Johnny
 * Date: 2017/1/19
 * Time: 21:13
 */
public class RandomHelloWorld {
    private static String randomString(int i) {
        Random ran = new Random(i);
        StringBuilder sb = new StringBuilder();
        while (true) {
            int k = ran.nextInt(27);
            System.out.println("char:" + k + ",number:" + k);
            if (k == 0)
                break;
            k += 96;
            sb.append((char) k);
        }
        return sb.toString();
    }

    //print hello world
    public static void main(String[] args) {
        System.out.println(randomString(-229985452) + " " + randomString(-147909649));
    }
}
