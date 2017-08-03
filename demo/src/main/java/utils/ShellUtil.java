package utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Author: Johnny
 * Date: 2017/8/3
 * Time: 20:34
 */
public class ShellUtil {
    private final static Logger logger = LoggerFactory.getLogger(ShellUtil.class);

    public static String execute(String[] commands) throws Exception {
        logger.info("Going to get value for vault secret [{}]", Arrays.toString(commands));
//        String[] commands = {"/bin/get_secret", secretKey};
        Process p = Runtime.getRuntime().exec(commands);
        p.waitFor();
        InputStream in = null;
        String scriptResults = "";
        try {
            in = new BufferedInputStream(p.getInputStream());
            scriptResults = IOUtils.toString(in);
        } catch (Exception e) {
            logger.error("Read file error!");
        } finally {
            if (in != null) {
                in.close();
            }
        }
        String vaultValue;
        if (p.exitValue() == 0) {
            if (scriptResults.startsWith("PASS=")) {
                vaultValue = scriptResults.substring("PASS=".length());
            } else {
                logger.error("Run shell [{}] results: [{}]", commands, scriptResults);
                throw new Exception(String.format("Failed to execute command get_secret, can't parse PASS in %s", scriptResults));
            }
        } else {
            logger.error("Run shell [{}] results: [{}]", commands, scriptResults);
            throw new Exception(String.format("Failed to execute script \"%s\", message: %s", Arrays.toString(commands), scriptResults));
        }
        return vaultValue;
    }
}
