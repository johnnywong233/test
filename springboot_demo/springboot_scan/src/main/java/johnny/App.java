package johnny;

import johnny.service.HelloService1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * scanBasePackages 填写johnny.*之后, @EnableAutoConfiguration(exclude={HelloService1.class})的exclude功能不生效
 * care: @@ComponentScan 的basePackages优先级大于@SpringBootApplication scanBasePackages
 * @author johnny
 */

//@EnableAutoConfiguration(excludeName={"johnny.service.HelloService1"})//Not work
//@EnableAutoConfiguration(exclude={HelloService1.class})//Not work
//@SpringBootApplication //默认情况, 则scanBasePackages=johnny
//@SpringBootApplication(scanBasePackages={"johnny.*", "service"})//both packages can be scanned
@ComponentScan(basePackages = {"johnny"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = HelloService1.class)
        })
@SpringBootApplication(scanBasePackages={"service"})//only scan package that was configured
//@SpringBootApplication(scanBasePackages={"service.*"})//configured wrong
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}