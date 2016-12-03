package algorithm.encrypt.secure;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 编解码基类
 */
public abstract class BasicCodec {
    private Base64Utils base64Utils;

    /**
     * 对称加密的密钥，经过base64编程的密钥字符串
     */
    protected String secretKey;

    /**
     * 非对称加密的公钥，经过base64编码的公钥字符串
     */
    protected String publicKey;

    /**
     * 非对称加密的私钥，经过base64编码的私钥字符串
     */
    protected String privateKey;

    public BasicCodec() {
        base64Utils = Base64Utils.getInstance();
    }

    public static final Charset charset = Charset.forName("UTF-8");

    /**
     * 加密
     *
     * @param data 需加密的内容
     * @return
     * @throws Exception
     */
    public abstract byte[] encrypt(byte[] data) throws Exception;

    /**
     * 解密
     *
     * @param data 需解密的内容
     * @return
     * @throws Exception
     */
    public abstract byte[] decrypt(byte[] data) throws Exception;

    /**
     * base64 编码
     *
     * @param data
     * @return
     */
    protected String encoder(byte[] data) {
        return base64Utils.encoder(data);
    }

    /**
     * base64解码
     *
     * @param data
     * @return
     * @throws IOException
     */
    protected byte[] decoder(String data) throws IOException {
        return base64Utils.decoder(data);
    }

    /**
     * 2进制数字转换为16进制字符串
     *
     * @param data
     * @return
     */
    public String parseByteArray2HexStr(byte[] data) {
        if (data == null || data.length < 1) {
            return null;
        }

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int h = data[i] & 0XFF;
            if (h < 16) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(h));
        }

        return hex.toString();
    }

    /**
     * 16进制字符串转换为2进制数字
     *
     * @param hex
     * @return
     */
    public byte[] parseHexStr2ByteArray(String hex) {
        if (hex == null || "".equals(hex)) {
            return null;
        }

        int length = hex.length() >> 1;
        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            int n = i << 1;
            int height = Integer.valueOf(hex.substring(n, n + 1), 16);
            int low = Integer.valueOf(hex.substring(n + 1, n + 2), 16);
            data[i] = (byte) (height * 16 + low);
        }
        return data;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

}