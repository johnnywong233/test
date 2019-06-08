package algorithm.encrypt.demo2;

import java.util.Base64;

/**
 * @author Johnny
 * Date: 2017/11/6
 * Time: 21:15
 * 编码格式，而非加密算法
 */
public class BASE64 {
    private static Base64.Decoder decoder = Base64.getDecoder();
    private static Base64.Encoder encoder = Base64.getEncoder();

    /**
     * BASE64解密
     */
    static byte[] decryptBASE64(String key) {
        return decoder.decode(key);
    }

    /**
     * BASE64加密
     */
    static String encryptBASE64(byte[] key) {
        return encoder.encodeToString(key);
    }

}
