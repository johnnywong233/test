import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: Johnny
 * Date: 2017/4/18
 * Time: 9:50
 */
@SpringBootApplication
public class DemoUploadfileApplication {

    /**
     * test page:
     * http://localhost:8080/upload
     * http://localhost:8080/upload/batch
     * see the uploaded files in the most parent pom.xml directory
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoUploadfileApplication.class, args);
    }
}
