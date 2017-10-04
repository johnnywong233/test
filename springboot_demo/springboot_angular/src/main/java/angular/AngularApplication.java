package angular;

import angular.config.JwtAuthenticationFilter;
import angular.config.PersistenceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
    private static final Logger logger = LoggerFactory.getLogger(AngularApplication.class);

    //https://github.com/ZhongjunTian/spring-boot-jwt-demo/tree/master/complete
    public static void main(String[] args) {
        SpringApplication.run(AngularApplication.class, args);
        logger.info("Spring boot angular demo case started! ");
    }

    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter("/api/**");

        //加了这个之后，不登录的情况下直接访问/
        // http://localhost:8080/student.html  看不到数据
        //http://localhost:8080/student/get?page=0&size=2  显示权限不够，看不懂json response body
        //但是有个问题，为什么login失败，报错400，Wrong credentials
        JwtAuthenticationFilter filter1 = new JwtAuthenticationFilter("/student/**");
        registrationBean.setFilter(filter);
        registrationBean.setFilter(filter1);
        return registrationBean;
    }

}
