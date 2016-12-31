package algorithm.encrypt.secure;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Utils {

    private static Base64Utils base64Utils = new Base64Utils();
    private BASE64Encoder base64Encoder;
    private BASE64Decoder base64Decoder;

    private Base64Utils() {
        base64Encoder = new BASE64Encoder();
        base64Decoder = new BASE64Decoder();
    }

    public static Base64Utils getInstance() {
        return base64Utils;
    }

    /**
     * base64 encode
     *
     * @param data
     * @return
     */
    public String encoder(byte[] data) {
        return base64Encoder.encodeBuffer(data);
    }

    /**
     * base64 decode
     *
     * @param data
     * @return
     * @throws IOException
     */
    public byte[] decoder(String data) throws IOException {
        return base64Decoder.decodeBuffer(data);
    }

}
