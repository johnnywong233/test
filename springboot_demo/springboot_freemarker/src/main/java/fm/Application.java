package fm;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 23:17
 */
@SpringBootApplication
@MapperScan("fm.dao")
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            String quote = restTemplate.getForObject(
                    "http://gturnquist-quoters.cfapps.io/api/random", String.class);
            log.info(quote);
        };
    }

    public static void main(String[] args) throws Exception {

        //set proxy in code to get rid of <strong>HPE</strong> clients should 'Use automatic configuration script' <strong>http://autocache.hpecorp.net/</strong>.
        System.setProperty("http.proxyHost", "web-proxy.atl.hpecorp.net");
        System.setProperty("http.proxyPort", "8080");
        SpringApplication.run(Application.class, args);
    }
}
