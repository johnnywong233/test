package service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    /**
     * 用于测试不在@SpringBootApplication 注解的类App所在的base package下面的@Service 类.
     * 怎么添加此类？
     * 方法一: 在application-bean.xml添加
     * <bean id="helloService" class="service.HelloService"/>
     * 然后写一个@ConfigClass,添加需要的注解
     * 方法二:在@ComponentScan注解里面配置excludeFilters
     * 而不需要xml配置文件以及@ConfigClass, 显然这种方法更简便
     *
     * 应用启动时,通过观察控制台是否打印下述信息, 来验证该@Service 是否被spring 管理.
     */
    public HelloService() {
        System.out.println("HelloService.HelloService()");
    }
}