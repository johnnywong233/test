package quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;
import java.time.Duration;

/**
 * Author: Johnny
 * Date: 2017/4/20
 * Time: 19:15
 */
@SpringBootApplication
public class QuartzApp {
    private static final Logger logger = LoggerFactory.getLogger(QuartzApp.class);

    @Value("${server.port}")
    private int port;
    @Value("${server.sessionTimeout}")
    private int sessionTimeout;

    //http://blog.csdn.net/loongshawn/article/details/52078134
    public static void main(String[] args) {
        logger.info("this is main class" + System.getProperty("file.encoding"));
        SpringApplication.run(QuartzApp.class, args);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(port);
        /// factory.setSessionTimeout(sessionTimeout, TimeUnit.SECONDS);
        factory.getSession().setTimeout(Duration.ofSeconds(sessionTimeout));
        return factory;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        // configure mas upload file size 100M
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.of(102400, DataUnit.KILOBYTES));
        factory.setMaxRequestSize(DataSize.of(102400, DataUnit.KILOBYTES));
        return factory.createMultipartConfig();
    }
}
