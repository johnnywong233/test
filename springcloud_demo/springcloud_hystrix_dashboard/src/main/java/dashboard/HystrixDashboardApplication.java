package dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Author: Johnny
 * Date: 2017/10/16
 * Time: 12:22
 */
@SpringCloudApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
public class HystrixDashboardApplication {
    public static void main(String[] args) {
            SpringApplication.run(HystrixDashboardApplication.class, args);
    }
}
