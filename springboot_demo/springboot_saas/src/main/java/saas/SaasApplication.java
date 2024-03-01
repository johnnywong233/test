package saas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:02
 */
@SpringBootApplication
public class SaasApplication extends SpringBootServletInitializer {
    //TODO
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SaasApplication.class, DbConfig.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SaasApplication.class, args);
    }
}