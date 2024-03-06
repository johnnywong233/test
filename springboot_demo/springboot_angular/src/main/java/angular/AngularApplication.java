package angular;

import angular.config.JwtAuthenticationFilter;
import angular.config.PersistenceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: Johnny
 * Date: 2017/9/20
 * Time: 22:06
 */
@Slf4j
@Import(PersistenceConfig.class)
@SpringBootApplication
public class AngularApplication implements WebMvcConfigurer {

    //https://github.com/ZhongjunTian/spring-boot-jwt-demo/tree/master/complete
    public static void main(String[] args) {
        SpringApplication.run(AngularApplication.class, args);
        log.info("Spring boot angular demo case started! ");
    }

    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        final FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
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
