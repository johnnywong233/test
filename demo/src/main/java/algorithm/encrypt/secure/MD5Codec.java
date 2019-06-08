package algorithm.encrypt.secure;

import java.security.MessageDigest;

/**
 * MD5 单向加密
 */
public class MD5Codec extends AbstractBasicCodec {
    private static final String ALGORITHM = "MD5";

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
     * 返回MD5单向加密后的十六进制字符串
     *
     * @param data to be encrypted
     * @return encrypted string
     */
    public String getEncryptForHex(byte[] data) throws Exception {
        byte[] digestData = encrypt(data);
        return parseByteArray2HexStr(digestData);
    }

}