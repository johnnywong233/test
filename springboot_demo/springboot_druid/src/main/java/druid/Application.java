package druid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Author: Johnny
 * Date: 2017/4/12
 * Time: 20:06
 * see <a href="http://blog.csdn.net/xiaoyu411502/article/details/51392237">...</a>
 */
//must import this, or will show a 404 page(Whitelabel Error Page)
@ServletComponentScan
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
