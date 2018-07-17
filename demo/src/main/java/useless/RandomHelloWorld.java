package useless;

import java.util.Random;

/**
 * Author: Johnny
 * Date: 2017/2/2
 * Time: 17:10
 */
public class RandomHelloWorld {

    public static void main(String[] args) {
        System.out.println(randomString(-229985452) + " " + randomString(-147909649));
        System.out.println(randomString1(-229985452) + " " + randomString1(-147909649));
    }

    public static String randomString(int i) {
        Random ran = new Random(i);
        StringBuilder sb = new StringBuilder();
        while (true) {
            int k = ran.nextInt(27);
            if (k == 0) {
                break;
            }
            sb.append((char) ('`' + k));
        }
        return sb.toString();
    }

    public static String randomString1(int i) {
        Random ran = new Random(i);
        StringBuilder sb = new StringBuilder();
        while (true) {
            int k = ran.nextInt(27);
//            System.out.println("char:" + k + ",number:" +  k);
            if (k == 0) {
                break;
            }
            k += 96;
            sb.append((char) k);
        }

        return sb.toString();
    }
}
