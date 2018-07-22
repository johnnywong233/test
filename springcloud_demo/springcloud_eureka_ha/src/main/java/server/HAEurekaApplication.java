package server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Author: Johnny
 * Date: 2017/9/14
 * Time: 19:20
 */
@EnableEurekaServer
@SpringBootApplication
public class HAEurekaApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(HAEurekaApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
