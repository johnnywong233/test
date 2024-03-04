package upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Author: Johnny
 * Date: 2017/4/18
 * Time: 9:50
 */
@SpringBootApplication
public class DemoUploadFileApplication extends SpringBootServletInitializer {

    /**
     * test page:
     * <a href="http://localhost:8080/upload">...</a>
     * <a href="http://localhost:8080/upload/batch">...</a>
     * see the uploaded files in the most parent pom.xml directory
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoUploadFileApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoUploadFileApplication.class);
    }

}
