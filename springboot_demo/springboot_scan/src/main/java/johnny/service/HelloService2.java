package johnny.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService2 {

    /**
     * 默认打印的信息，即不需要配置Component scan, @SpringBootApplication 注解会配置scan的basepackage
     * 启动的时候观察控制台是否打印此信息;
     */
    public HelloService2() {
        System.out.println("HelloService2.HelloService2()");
    }
}
