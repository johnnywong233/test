package client.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/9/14
 * Time: 18:32
 */
@Slf4j
@RestController
public class HelloController {
    @Resource
    private DiscoveryClient client;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
        List<ServiceInstance> instances = client.getInstances("compute-service");
        for (ServiceInstance instance : instances) {
            log.info("/hello,host:" + instance.getHost() + ",service_id:" + instance.getServiceId());
        }
        return "Hello World";
    }
}
