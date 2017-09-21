package upload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Author: Johnny
 * Date: 2017/4/18
 * Time: 9:50
 */
//comment this class annotation to see that just an application.properties config is OK for file size config
@Configuration
public class FileUploadConfiguration {

    @Value("${spring.http.multipart.location}")
    private String location;
    @Value("${spring.http.multipart.max-file-size}")
    private String maxFileSize;
    @Value("${spring.http.multipart.max-request-size}")
    private String maxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //set file size, over this threshold will throw exception
        //over this threshold, then we should try catch and deal with exception messages
        factory.setMaxFileSize(maxFileSize); // KB,MB
        //set max total upload file size
        factory.setMaxRequestSize(maxRequestSize);
        //Set the directory location where files will be stored. not working!!
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}