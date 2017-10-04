package demo1;

import demo1.config.JwtAuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMvcQuickstartApplication {

    public static void main(String[] args) {

        System.setProperty("spring.devtools.restart.enabled", "true");//彻底禁用重启支持
        System.setProperty("spring.devtools.livereload.enabled", "false");//彻底禁用devtools内嵌的LiveReload服务器

        //使用代理连接远程应用
        System.setProperty("spring.devtools.remote.proxy.host", "web-proxy.atl.hpecorp.net");
        System.setProperty("spring.devtools.remote.proxy.port", "8080");

        SpringApplication.run(SpringMvcQuickstartApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
                "/api/**");
        registrationBean.setFilter(filter);
        return registrationBean;
    }
}
