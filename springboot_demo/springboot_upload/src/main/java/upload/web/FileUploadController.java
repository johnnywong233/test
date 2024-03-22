package upload.web;

import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Author: Johnny
 * Date: 2017/4/18
 * Time: 9:49
 */
@Slf4j
@RestController
public class FileUploadController {

    //url: http://ip:port/upload
    @ApiOperation(value = "GET method of upload single files", notes = "upload single files")
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "fileUpload";
    }

    //url: http://ip:port/upload/batch
    @ApiOperation(value = "GET method of upload multi files", notes = "upload multi files")
    @RequestMapping(value = "/upload/batch", method = RequestMethod.GET)
    public String batchUpload() {
        return "multiFileUpload";
    }

    @ApiOperation(value = "POST method of upload single file", notes = "upload single file")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                //just simple demo; upload to where the most parent pom.xml directory
                //can be set to designed location @see MultipartConfigFactory.setLocation
                //and can set some specific format file
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(Objects.requireNonNull(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                log.error("upload fail", e);
                return "upload failure," + e.getMessage();
            }
            return "upload successfully";
        } else {
            return "upload failure due to empty file.";
        }
    }

    @ApiOperation(value = "POST method of upload multi files", notes = "upload multi files")
    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    public String batchUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for (int i = 0; i < files.size(); ++i) {
            MultipartFile file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(Objects.requireNonNull(file.getOriginalFilename())));
                    stream.write(file.getBytes());
                    stream.close();
                } catch (Exception e) {
                    return "You failed to upload " + i + " => " + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " because the file was empty.";
            }
        }
        return "upload successful";
    }
}