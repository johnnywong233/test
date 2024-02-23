package utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.zip.Inflater;

/**
 * Author: Johnny
 * Date: 2016/12/5
 * Time: 20:11
 */
@Slf4j
public class ZipUtils {

    //zip
    private static String zlibInflate(byte[] bytes, int resultSizeLimit) throws Exception {
        // Decompress the bytes
        Inflater deCompressor = new Inflater();
        deCompressor.setInput(bytes);
        byte[] result = new byte[resultSizeLimit];
        int resultLength = deCompressor.inflate(result);
        deCompressor.end();
        if (resultLength == resultSizeLimit) {
            log.info("Access result limit, going to double result size and zlib inflate again.");
            return zlibInflate(bytes, 2 * resultSizeLimit);
        } else {
            return new String(result, 0, resultLength, StandardCharsets.UTF_8);
        }
    }
}
