package mybatis.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeJpaRepository = ctx.getBean(EmployeeJpaRepository.class);
        System.out.println("setup");
    }

    @After
    public void tearDown() throws Exception {
        ctx = null;
        System.out.println("tearDown");
    }

    @Test
    public void testFind() {
        System.out.println("employee: " + employeeJpaRepository.findOne(4));
        System.out.println("employee(8): " + employeeJpaRepository.exists(8));
        System.out.println("employee(108): " + employeeJpaRepository.exists(108));
    }

}