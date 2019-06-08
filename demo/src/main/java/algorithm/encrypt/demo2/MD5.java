package algorithm.encrypt.demo2;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 通常不直接使用MD5加密，而是将MD5产生的字节数组交给BASE64再'加密'一把，得到相应的字符串
 *
 * @author Johnny
 * Date: 2017/11/6
 * Time: 21:32
 */
public class MD5 {
    private static final String KEY_MD5 = "MD5";

    private static String getResult(String inputStr) {
        BigInteger bigInteger = null;
        try {
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bigInteger.toString(16);
    }

}
