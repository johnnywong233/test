package mybatis.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.TestPropertySource;

/**
 * Author: Johnny
 * Date: 2017/9/17
 * Time: 16:00
 */
@TestPropertySource(locations = "classpath:application-test.yml")
public class EmployeeServiceTest {

    private ApplicationContext ctx = null;
    private EmployeeService employeeService = null;

    @BeforeEach
    public void setup() {
        ctx = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeService = ctx.getBean(EmployeeService.class);
        System.out.println("setup");
    }

    @AfterEach
    public void tearDown() {
        ctx = null;
        System.out.println("tearDown");
    }

    @Test
    public void testUpdate() {
        employeeService.update(1, 55);
    }

}