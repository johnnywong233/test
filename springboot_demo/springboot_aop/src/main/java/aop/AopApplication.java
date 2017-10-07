package aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 14:20
 */
@SpringBootApplication
//强制使用Cglib代理
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCaching
public class AopApplication {
    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }
}
