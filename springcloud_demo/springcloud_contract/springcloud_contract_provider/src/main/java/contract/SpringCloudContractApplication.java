package contract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Author: Johnny
 * Date: 2017/11/21
 * Time: 11:35
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"contract"})
@EntityScan(basePackages = {"contract"})
public class SpringCloudContractApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudContractApplication.class, args);
    }
}
