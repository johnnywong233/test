package algorithm.encrypt.secure;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESCodec extends AbstractBasicCodec {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;

    public AESCodec() throws NoSuchAlgorithmException {
        super();
        initKey();
    }

    public AESCodec(String secretKey) {
        super.secretKey = secretKey;
    }

    @Override
    public byte[] encrypt(byte[] data) throws Exception {
        if (secretKey == null || "".equals(secretKey)) {
            throw new Exception("secret Key need to exists");
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(decoder(secretKey), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }

    @Override
    public byte[] decrypt(byte[] data) throws Exception {
        if (secretKey == null || "".equals(secretKey)) {
            throw new Exception("secret Key need to exists");
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(decoder(secretKey), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }

    private void initKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(KEY_SIZE, secureRandom);
        SecretKey aesKey = keyGenerator.generateKey();
        super.secretKey = encoder(aesKey.getEncoded());
    }

}