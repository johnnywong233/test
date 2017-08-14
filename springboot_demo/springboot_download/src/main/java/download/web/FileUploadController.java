package download.web;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static download.util.FileUtil.convert2String;


/**
 * Author: Johnny
 * Date: 2017/7/11
 * Time: 11:33
 */
@Controller
public class FileUploadController {
    private final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @ApiOperation(value = "return static upload yaml html page", notes = "return static upload yaml html page")
    @RequestMapping(value = "/upload/yaml", method = RequestMethod.GET)
    public String upload() {
        return "yamlFileUpload";
    }

    @ApiOperation(value = "return static upload json html page", notes = "return static upload json html page")
    @RequestMapping(value = "/upload/json", method = RequestMethod.GET)
    public String uploadJson() {
        return "/jsonFileUpload";
    }

    @RequestMapping(value = "/upload/batch", method = RequestMethod.GET)
    public String batchUpload() {
        return "/multiFileUpload";
    }

    @ApiOperation(value = "Upload yaml file", notes = "Upload yaml file")
    @RequestMapping(value = "/upload/yaml", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        return convert2String(file);
    }

    @ApiOperation(value = "Upload json file", notes = "Upload json file")
    @RequestMapping(value = "/upload/json", method = RequestMethod.POST)
    @ResponseBody
    public String uploadJson(@RequestParam("file") MultipartFile file) {
        return convert2String(file);
    }

    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    @ResponseBody
    public String batchUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file;
        BufferedOutputStream stream;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    logger.error("Upload failure due to empty file [{}]", file.getOriginalFilename());
                    return "Failed to upload " + i + " => " + e.getMessage();
                }
            } else {
                logger.warn("Upload failure due to empty file [{}]", file.getOriginalFilename());
                return "Failed to upload " + i + " because the file was empty.";
            }
        }
        return "upload successful";
    }
}
