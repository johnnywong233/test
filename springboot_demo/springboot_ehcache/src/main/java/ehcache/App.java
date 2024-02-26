package ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * create table demo_info (
 * id    serial primary key,
 * name  varchar(255),
 * pwd   varchar(255),
 * state integer not null
 * );
 * Author: Johnny
 * Date: 2017/4/17
 * Time: 23:35
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
