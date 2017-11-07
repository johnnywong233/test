package algorithm.encrypt.demo2;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Author: Johnny
 * Date: 2017/11/6
 * Time: 21:43
 */
public class SHA {
    private static final String KEY_SHA = "SHA";

    public static String getResult(String inputStr) {
        BigInteger sha = null;
        byte[] inputData = inputStr.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha.toString(32);
    }

}
