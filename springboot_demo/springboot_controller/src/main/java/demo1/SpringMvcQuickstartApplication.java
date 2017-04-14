package demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMvcQuickstartApplication {

    public static void main(String[] args) {

        System.setProperty("spring.devtools.restart.enabled", "true");//彻底禁用重启支持
        System.setProperty("spring.devtools.livereload.enabled", "false");//彻底禁用devtools内嵌的LiveReload服务器

        //使用代理连接远程应用
        System.setProperty("spring.devtools.remote.proxy.host", "web-proxy.sgp.hp.com");
        System.setProperty("spring.devtools.remote.proxy.port", "8080");

        SpringApplication.run(SpringMvcQuickstartApplication.class, args);
    }
}
