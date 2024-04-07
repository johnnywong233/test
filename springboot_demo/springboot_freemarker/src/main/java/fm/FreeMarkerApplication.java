package fm;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@SpringBootApplication
//No MyBatis mapper was found in '[fm.mapper.**]' package. Please check your configuration.
//@MapperScan("fm.mapper.**")
//@MapperScan(basePackages = "fm.mapper") //也不对
//傻啊，这个是用来配置扫描 mapper.xml 文件的，不是 mapper 接口。
public class FreeMarkerApplication {
    public static void main(String[] args) {

        //set proxy in code to get rid of <strong>HPE</strong> clients should 'Use automatic configuration script' <strong>http://autocache.hpecorp.net/</strong>.
//        System.setProperty("http.proxyHost", "web-proxy.atl.hpecorp.net");
//        System.setProperty("http.proxyPort", "8080");
        SpringApplication.run(FreeMarkerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> restTemplate.getForObject("https://www.google.com/", String.class);
    }
}
