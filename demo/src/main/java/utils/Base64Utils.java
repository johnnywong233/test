package utils;

import java.util.Base64;

public class Base64Utils {

    private static Base64Utils base64Utils = new Base64Utils();
    private Base64.Encoder encoder;
    private Base64.Decoder decoder;

    private Base64Utils() {
        encoder = Base64.getEncoder();
        decoder = Base64.getDecoder();
    }

    public static Base64Utils getInstance() {
        return base64Utils;
    }

    /**
     * base64 encode
     */
    public String encoder(byte[] data) {
        return encoder.encodeToString(data);
    }

    /**
     * base64 decode
     */
    public byte[] decoder(String data) {
        return decoder.decode(data);
    }

}
