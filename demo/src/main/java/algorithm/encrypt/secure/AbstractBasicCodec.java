package algorithm.encrypt.secure;

import lombok.Getter;
import utils.Base64Utils;

/**
 * 编解码基类
 */
@Getter
public abstract class AbstractBasicCodec {
    private final Base64Utils base64Utils;
    /**
     * 对称加密的密钥，经过base64编程的密钥字符串
     */
    String secretKey;
    /**
     * 非对称加密的公钥，经过base64编码的公钥字符串
     */
    String publicKey;
    /**
     * 非对称加密的私钥，经过base64编码的私钥字符串
     */
    String privateKey;

    AbstractBasicCodec() {
        base64Utils = Base64Utils.getInstance();
    }

    /**
     * 加密
     */
    public abstract byte[] encrypt(byte[] data) throws Exception;

    /**
     * 解密
     */
    public abstract byte[] decrypt(byte[] data) throws Exception;

    /**
     * base64 编码
     */
    String encoder(byte[] data) {
        return base64Utils.encoder(data);
    }

    /**
     * base64解码
     */
    byte[] decoder(String data) {
        return base64Utils.decoder(data);
    }

    /**
     * 2进制数字转换为16进制字符串
     */
    public String parseByteArray2HexStr(byte[] data) {
        if (data == null || data.length < 1) {
            return null;
        }

        StringBuilder hex = new StringBuilder();
        for (byte aData : data) {
            int h = aData & 0XFF;
            if (h < 16) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(h));
        }
        return hex.toString();
    }

    /**
     * 16进制字符串转换为2进制数字
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

}