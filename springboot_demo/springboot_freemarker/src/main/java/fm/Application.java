package fm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 23:17
 */
@SpringBootApplication
@MapperScan("fm.dao")
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
