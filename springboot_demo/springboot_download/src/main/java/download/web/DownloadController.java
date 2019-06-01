package download.web;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Author: Johnny
 * Date: 2017/7/20
 * Time: 23:12
 */
@Controller
public class DownloadController {

    @ApiOperation(value = "test download yaml file", notes = "download yaml file for show yaml files format and usage.")
    @RequestMapping(value = "downloadYaml", method = RequestMethod.GET)
    public String testDownloadYaml() {
        return "/downloadYaml";
    }

    @ApiOperation(value = "test download yaml file", notes = "download yaml file for show yaml files format and usage.")
    @RequestMapping(value = "/downloadYaml", method = RequestMethod.POST)
    public ResponseEntity<byte[]> downloadYaml(@RequestParam("filename") String filename) throws IOException {
        String downloadFilePath = "C:";
        File file = new File(downloadFilePath + File.separator + filename);
        HttpHeaders headers = new HttpHeaders();
        String downloadFileName = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        headers.setContentDispositionFormData("attachment", downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

}
