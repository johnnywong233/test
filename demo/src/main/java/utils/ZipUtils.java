package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.zip.Inflater;

/**
 * Author: Johnny
 * Date: 2016/12/5
 * Time: 20:11
 */
public class ZipUtils {
    private static Logger logger = LoggerFactory.getLogger(ZipUtils.class);

    //zip
    private static String zlibInflate(byte[] Bytes, int resultSizeLimit) throws Exception {
        // Decompress the bytes
        Inflater deCompressor = new Inflater();
        deCompressor.setInput(Bytes);
        byte[] result = new byte[resultSizeLimit];
        int resultLength = deCompressor.inflate(result);
        deCompressor.end();
        if (resultLength == resultSizeLimit) {
            logger.info("Access result limit, going to double result size and zlib inflate again.");
            return zlibInflate(Bytes, 2 * resultSizeLimit);
        } else {
            return new String(result, 0, resultLength, "UTF-8");
        }
    }
}
