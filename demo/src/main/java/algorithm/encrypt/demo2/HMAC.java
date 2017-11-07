package algorithm.encrypt.demo2;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author: Johnny
 * Date: 2017/11/6
 * Time: 21:13
 * 基础加密组件
 */
public class HMAC {
    private static final String KEY_MAC = "HmacMD5";

    /**
     * 初始化HMAC密钥
     */
    private static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return BASE64.encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC加密  ：主要方法
     */
    private static String encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(BASE64.decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return new String(mac.doFinal(data));
    }

    private static String getResult(String inputStr) {
        String result = null;
        try {
            byte[] inputData = inputStr.getBytes();
            String key = HMAC.initMacKey();
            result = HMAC.encryptHMAC(inputData, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String args[]) {
        try {
            String inputStr = "silly_test";
            getResult(inputStr);
            getResult(inputStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
