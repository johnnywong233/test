package angular.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * Author: Johnny
 * Date: 2017/10/4
 * Time: 12:59
 */
@Slf4j
public class EncryptUtil {
    private static final char PADDING_CHAR = '\0';
    private static String IV = "abababababababab";
    private static String encryptionKey = "abcdef0123456789";

    static String encrypt(String plainText) throws Exception {
        plainText = padding(plainText);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
        byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return new String(bytes, StandardCharsets.ISO_8859_1);
    }

    static String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
        byte[] decoded = cipherText.getBytes(StandardCharsets.ISO_8859_1);
        String s = new String(cipher.doFinal(decoded), StandardCharsets.UTF_8);
        return unPadding(s);
    }

    private static String padding(String data) {
        StringBuilder sb = new StringBuilder(data);
        // length has to be multiple of 16
        int lengthFactor = 16;
        while (sb.length() % lengthFactor != 0) {
            sb.append(PADDING_CHAR);
        }
        return sb.toString();
    }

    private static String unPadding(String data) {
        StringBuilder sb = new StringBuilder(data);
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == PADDING_CHAR) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
