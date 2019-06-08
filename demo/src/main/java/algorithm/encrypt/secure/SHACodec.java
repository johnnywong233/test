package algorithm.encrypt.secure;

import java.security.MessageDigest;

/**
 * SHA 单向加密
 */
public class SHACodec extends AbstractBasicCodec {
    private static final String ALGORITHM = "SHA";

    @Override
    public byte[] encrypt(byte[] data) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        return messageDigest.digest(data);
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return null;
    }

    /**
     * 返回SHA单向加密后的十六进制字符串
     */
    public String getEncryptForHex(byte[] data) throws Exception {
        byte[] digestData = encrypt(data);
        return parseByteArray2HexStr(digestData);
    }
}
