package algorithm.encrypt.demo2;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Author: Johnny
 * Date: 2017/11/6
 * Time: 21:15
 * 编码格式，而非加密算法
 */
public class BASE64 {
    /**
     * BASE64解密
     */
    static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     */
    static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

}
