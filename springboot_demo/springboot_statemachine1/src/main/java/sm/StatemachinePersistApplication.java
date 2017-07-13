package sm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.config.EnableStateMachine;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 21:24
 */
@SpringBootApplication
@EnableStateMachine
public class StatemachinePersistApplication {
    //http://blog.sina.com.cn/s/blog_7d1968e20102wxm2.html
    public static void main(String[] args) {
        SpringApplication.run(StatemachinePersistApplication.class, args);
    }
}
