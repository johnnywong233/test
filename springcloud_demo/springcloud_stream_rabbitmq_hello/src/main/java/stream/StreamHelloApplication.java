package stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: Johnny
 * Date: 2017/11/8
 * Time: 23:29
 * 不需要使用配置文件，默认即可；Resource下面的配置文件用于另一个App
 */
@SpringBootApplication
public class StreamHelloApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamHelloApplication.class, args);
    }
}
