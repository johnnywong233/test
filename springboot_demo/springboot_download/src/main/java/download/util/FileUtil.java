package download.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Author: Johnny
 * Date: 2017/7/12
 * Time: 17:15
 */
@Slf4j
public class FileUtil {
    /**
     * convert MultipartFile to File
     */
    public static File convert2File(MultipartFile file) throws IOException {
        File conFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
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
        log.info("Running method isJSONValid to check file [{}]", fileName);
        String jsonStr = IOUtils.toString(Objects.requireNonNull(Object.class.getResourceAsStream(fileName)), StandardCharsets.UTF_8);
        boolean isObject = true;
        boolean isArray = true;
        try {
            new JSONObject(jsonStr);
        } catch (JSONException ex) {
            isObject = false;
        }
        try {
            new JSONArray(jsonStr);
        } catch (JSONException ex1) {
            isArray = false;
        }
        if (!isObject && !isArray) {
            log.error("Input jsonStr is not a valid JSONArray nor a valid JSONArray", jsonStr);
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
        try (InputStream in = new BufferedInputStream(p.getInputStream())) {
        } catch (Exception e) {
            log.error("Read script file [{}] error!", command[0]);
        }
        if (p.exitValue() == 0) {
            log.info("Succeed checking the given yaml [{}] format with script [{}]", fileName, command[0]);
            return true;
        } else {
            log.warn("Failed checking the given yaml [{}] format with script [{}]", fileName, command[0]);
            return false;
        }
    }

    /**
     * convert MultipartFile to String
     */
    public static String convert2String(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(Objects.requireNonNull(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                log.error("Upload failure, due to file [{}] not found", file.getOriginalFilename());
                return "Upload failure," + e.getMessage();
            } catch (IOException e) {
                log.error("Upload failure", e);
                return "Upload failure," + e.getMessage();
            }
            //TODO: can return a html like controller?
            return "Upload successfully";
//            return "/success";
        } else {
            log.warn("Upload failure due to empty file.");
            return "Upload failure due to empty file.";
//            return "/error";
        }
    }
}
