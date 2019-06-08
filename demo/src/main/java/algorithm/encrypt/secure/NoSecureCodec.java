package algorithm.encrypt.secure;

/**
 * 不使用任何安全加密
 */
public class NoSecureCodec extends AbstractBasicCodec {

    @Override
    public byte[] encrypt(byte[] data) {
        return data;
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return data;
    }

}
