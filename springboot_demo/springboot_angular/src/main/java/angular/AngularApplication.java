package angular;

import angular.config.PersistenceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Author: Johnny
 * Date: 2017/9/20
 * Time: 22:06
 */
@EnableAutoConfiguration
@Import(PersistenceConfig.class)
@SpringBootApplication
public class AngularApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(AngularApplication.class, args);
    }

    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
