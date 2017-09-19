package mybatis.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

    @Before
    public void setup() {
        ctx = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeService = ctx.getBean(EmployeeService.class);
        System.out.println("setup");
    }

    @After
    public void tearDown() {
        ctx = null;
        System.out.println("tearDown");
    }

    @Test
    public void testUpdate() {
        employeeService.update(1, 55);
    }

}