package algorithm.encrypt.secure;

/**
 * 不使用任何安全加密
 */
public class NoSecureCodec extends BasicCodec {

    @Override
    public byte[] encrypt(byte[] data) throws Exception {
        return data;
    }

    @Override
    public byte[] decrypt(byte[] data) throws Exception {
        return data;
    }

}
