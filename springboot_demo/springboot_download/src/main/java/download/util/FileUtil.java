package download.util;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: Johnny
 * Date: 2017/7/12
 * Time: 17:15
 */
public class FileUtil {

    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * convert MultipartFile to File
     */
    public static File convert2File(MultipartFile file) throws IOException {
        File conFile = new File(file.getOriginalFilename());
        conFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(conFile);
        fos.write(file.getBytes());
        fos.close();
        return conFile;
    }

    /**
     * check if is a valid json file
     */
    public static boolean isJSONValid(String fileName) throws IOException {
        logger.info("Running method isJSONValid to check file [{}]", fileName);
        String jsonStr = IOUtils.toString(Object.class.getResourceAsStream(fileName));
        boolean isObject = true;
        boolean isArray = true;
        try {
            new JSONObject(jsonStr);
        } catch (JSONException ex) {
            isObject = false;
            logger.warn("Input jsonStr is not a valid JSONObject ", jsonStr);
        }
        try {
            new JSONArray(jsonStr);
        } catch (JSONException ex1) {
            isArray = false;
            logger.warn("Input jsonStr is not a valid JSONArray ", jsonStr);
        }
        if (!isObject && !isArray) {
            logger.error("Input jsonStr is not a valid JSONArray nor a valid JSONArray", jsonStr);
        }
        return isObject || isArray;
    }

    /**
     * check with script
     */
    public static boolean checkYAMLValid(String fileName) throws Exception {
        String[] command = {"yamlcheck", "-d", fileName};
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        InputStream in = null;
        try {
            in = new BufferedInputStream(p.getInputStream());
        } catch (Exception e) {
            logger.error("Read script file [{}] error!", command[0]);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        if (p.exitValue() == 0) {
            logger.info("Succeed checking the given yaml [{}] format with script [{}]", fileName, command[0]);
            return true;
        } else {
            logger.warn("Failed checking the given yaml [{}] format with script [{}]", fileName, command[0]);
            return false;
        }
    }

    /**
     * convert MultipartFile to String
     */
    public static String convert2String(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                logger.error("Upload failure, due to file [{}] not found", file.getOriginalFilename());
                return "Upload failure," + e.getMessage();
            } catch (IOException e) {
                logger.error("Upload failure", e.getMessage());
                return "Upload failure," + e.getMessage();
            }
            //TODO: can return a html like controller?
            return "Upload successfully";
//            return "/success";
        } else {
            logger.warn("Upload failure due to empty file.");
            return "Upload failure due to empty file.";
//            return "/error";
        }
    }


    public static void main(String[] args) throws Exception {
    }
}
