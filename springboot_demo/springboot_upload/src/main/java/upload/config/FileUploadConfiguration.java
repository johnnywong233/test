package upload.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

/**
 * Author: Johnny
 * Date: 2017/4/18
 * Time: 9:50
 */
public class FileUploadConfiguration {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //set file size, over this threshold will throw exception
        //over this threshold, then we should try catch and deal with exception messages
        factory.setMaxFileSize("256KB"); // KB,MB
        //set max total upload file size
        factory.setMaxRequestSize("512KB");
        //TODO
        //特定格式文件上传
        // Set the directory location where files will be stored.
        factory.setLocation("");
        return factory.createMultipartConfig();
    }
}