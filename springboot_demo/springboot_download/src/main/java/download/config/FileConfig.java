package download.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * @author Johnny
 * Date: 2017/7/11
 * Time: 11:36
 */
@Configuration
public class FileConfig {

    @Value("${multipart.max.FileSize}")
    private Integer fileSize;
    @Value("${multipart.max.RequestSize}")
    private Integer requestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // set file size, over this threshold will throw exception
        // over this threshold, then we should try catch and deal with exception messages
        factory.setMaxFileSize(DataSize.of(fileSize, DataUnit.MEGABYTES));
        factory.setMaxFileSize(DataSize.of(requestSize, DataUnit.MEGABYTES));
        // Set the directory location where files will be stored.
        factory.setLocation("");
        return factory.createMultipartConfig();
    }
}
