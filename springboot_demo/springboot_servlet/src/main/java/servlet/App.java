package servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import servlet.servlet.MyServlet1;

@SpringBootApplication
@ServletComponentScan
public class App {

    /**
     * 注册Servlet.不需要添加注解：@ServletComponentScan
     */
    @SuppressWarnings("deprecation")
	@Bean
    public ServletRegistrationBean MyServlet1() {
        return new ServletRegistrationBean(new MyServlet1(), "/myServlet1/*");
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
