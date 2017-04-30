package redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: Johnny
 * Date: 2017/4/25
 * Time: 20:03
 */
@SpringBootApplication
public class App {

    //-javaagent:.\lib\springloaded-1.2.4.RELEASE.jar -noverify
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
