package dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Author: Johnny
 * Date: 2017/10/16
 * Time: 12:22
 */
@SpringBootApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
public class HystrixDashboardApplication {
    public static void main(String[] args) {
            SpringApplication.run(HystrixDashboardApplication.class, args);
    }
}
