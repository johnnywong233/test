package algorithm.encrypt.factory;

import java.security.NoSuchAlgorithmException;

import algorithm.encrypt.other.SecureType;
import algorithm.encrypt.secure.AESCodec;
import algorithm.encrypt.secure.BasicCodec;
import algorithm.encrypt.secure.DESCodec;
import algorithm.encrypt.secure.MD5Codec;
import algorithm.encrypt.secure.NoSecureCodec;
import algorithm.encrypt.secure.RSAForPrivateCodec;
import algorithm.encrypt.secure.RSAForPublicCodec;
import algorithm.encrypt.secure.SHACodec;

public class SecureFactory {

    public static BasicCodec getCodec(SecureType type, String key) throws NoSuchAlgorithmException {
        BasicCodec codec;
        switch (type) {
            case MD5:
                codec = new MD5Codec();
                break;
            case SHA:
                codec = new SHACodec();
                break;
            case DES:
                if (key != null && !"".equals(key)) {
                    codec = new DESCodec(key);
                } else {
                    codec = new DESCodec();
                }
                break;
            case AES:
                if (key != null && !"".equals(key)) {
                    codec = new AESCodec(key);
                } else {
                    codec = new AESCodec();
                }
                break;
            case RSA_PRIVATE:
                codec = new RSAForPrivateCodec();
                break;
            case RSA_PUBLIC:
                codec = new RSAForPublicCodec(key);
                break;
            default:
                codec = new NoSecureCodec();
        }
        return codec;
    }
}
