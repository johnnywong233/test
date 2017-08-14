package client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 22:58
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ComputeServiceApplication  {
//    public static void main(String[] args) {
//        new SpringApplicationBuilder(ComputeServiceApplication .class).web(true).run(args);
//    }

    public static void main(String[] args) {
        SpringApplication.run(ComputeServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
