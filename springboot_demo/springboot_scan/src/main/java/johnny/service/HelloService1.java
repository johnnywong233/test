package johnny.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService1 {
	/**
	 * 测试@EnableAutoConfiguration(exclude={HelloService.class})的exclude功能,或者@ComponentScan 的excludeFilters功能
	 */
	public HelloService1() {
		System.out.println("HelloService1.HelloService1()");
	}

}
