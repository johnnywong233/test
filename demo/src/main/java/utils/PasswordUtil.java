package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author: Johnny
 * Date: 2017/9/25
 * Time: 22:08
 */
public class PasswordUtil {

    private static Cipher deCipher;
    private static Cipher enCipher;
    private SecretKeySpec key;
    private IvParameterSpec ivSpec;

    /**
     * 使用3Des进行加密。
     *
     * @param string 待加密的字符串
     * @param key    密钥
     * @return 加密后的字符串
     */
    public static String tripleEncrypt(String string, String key) throws Exception {
        if (key.length() >= 48) {
            byte[] bytK1 = StringUtil.hexString2byteArray(key.substring(0, 16));
            byte[] bytK2 = StringUtil.hexString2byteArray(key.substring(16, 32));
            byte[] bytK3 = StringUtil.hexString2byteArray(key.substring(32, 48));

            byte[] bytP = string.getBytes();
            byte[] ep = encrypt(encrypt(encrypt(bytP, bytK1), bytK2), bytK3);

            return StringUtil.byteArray2HexString(ep);
        } else {
            throw new Exception("密钥长度错误，无法进行3DES加密");
        }
    }

    private static byte[] encrypt(byte[] contentBytes, byte[] keyBytes) throws Exception {
        return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
    }


    private static byte[] decrypt(byte[] contentBytes, byte[] keyBytes) throws Exception {
        return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
    }

    //https://segmentfault.com/a/1190000011156401
    private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        //SIXTEEN_CHAR_INIT_VECTOR
        byte[] initParam = "".getBytes("utf-8");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

        Cipher cipher = Cipher.getInstance("AES");//CBC/PKCS5Padding
        cipher.init(mode, secretKey, ivParameterSpec);
        return cipher.doFinal(contentBytes);
    }

    /**
     * 使用3Des进行解密。
     *
     * @param string 待解密的字符串
     * @param key    密钥
     * @return 解密后的字符串
     */
    public static String tripleDecrypt(String string, String key) throws Exception {
        if (key.length() >= 48) {
            byte[] bytK1 = StringUtil.hexString2byteArray(key.substring(0, 16));
            byte[] bytK2 = StringUtil.hexString2byteArray(key.substring(16, 32));
            byte[] bytK3 = StringUtil.hexString2byteArray(key.substring(32, 48));

            byte[] bytP = StringUtil.hexString2byteArray(string);
            byte[] dp = decrypt(decrypt(decrypt(bytP, bytK3), bytK2), bytK1);
            return new String(dp);
        } else {
            throw new Exception("密钥长度错误，无法进行3DES加密");
        }
    }

}