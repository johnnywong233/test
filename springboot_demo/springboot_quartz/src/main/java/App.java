import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2017/4/20
 * Time: 19:15
 */
//@ComponentScan
//@Configuration
@SpringBootApplication
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Value("${server.port}")
    private int port;
    @Value("${server.sessionTimeout}")
    private int sessionTimeout;

    public static void main(String[] args) {
        logger.info("this is main class" + System.getProperty("file.encoding"));
        SpringApplication.run(App.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(port);
        factory.setSessionTimeout(sessionTimeout, TimeUnit.SECONDS);
        return factory;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        //configure mas upload file size 100M
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("102400KB");
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }
}
