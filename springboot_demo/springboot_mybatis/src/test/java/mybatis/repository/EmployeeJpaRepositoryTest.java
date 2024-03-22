package mybatis.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: Johnny
 * Date: 2017/9/19
 * Time: 21:29
 */
public class EmployeeJpaRepositoryTest {

    private ApplicationContext ctx = null;

    private EmployeeJpaRepository employeeJpaRepository = null;

    @BeforeEach
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeJpaRepository = ctx.getBean(EmployeeJpaRepository.class);
        System.out.println("setup");
    }

    @AfterEach
    public void tearDown() {
        ctx = null;
        System.out.println("tearDown");
    }

    @Test
    public void testFind() {
        System.out.println("employee: " + employeeJpaRepository.findById(4));
        System.out.println("employee(8): " + employeeJpaRepository.existsById(8));
        System.out.println("employee(108): " + employeeJpaRepository.existsById(108));
    }

}