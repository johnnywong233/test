package mybatis.repository;

import mybatis.domain.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/9/17
 * Time: 16:02
 */
@TestPropertySource(locations = "classpath:application-test.yml")
public class EmployeeRepositoryTest {

    private ApplicationContext ctx = null;

    private EmployeeRepository employeeRepository = null;

    @Before
    public void setup() {
        ctx = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeRepository = ctx.getBean(EmployeeRepository.class);
        System.out.println("setup");
    }

    @After
    public void tearDown() {
        ctx = null;
        System.out.println("tearDown");
    }

    @Test
    public void testFindByName() {
        System.out.println(employeeRepository);
        //matter the upper/lower case
        Employee employee = employeeRepository.findByName("Ward");
        System.out.println("id:" + employee.getId() + " , name:" + employee.getName() + " ,age:" + employee.getAge());
    }

    @Test
    public void testFindByNameStartingWithAndAgeLessThan() {
        List<Employee> employees = employeeRepository.findByNameStartingWithAndAgeLessThan("test", 22);

        for (Employee employee : employees) {
            System.out.println("id:" + employee.getId() + " , name:" + employee.getName() + " ,age:" + employee.getAge());
        }
    }

    @Test
    public void testFindByNameEndingWithAndAgeLessThan() {
        List<Employee> employees = employeeRepository.findByNameEndingWithAndAgeLessThan("6", 23);

        for (Employee employee : employees) {
            System.out.println("id:" + employee.getId() + " , name:" + employee.getName() + " ,age:" + employee.getAge());
        }
    }

    @Test
    public void testFindByNameInOrAgeLessThan() {
        List<String> names = new ArrayList<>();
        names.add("test1");
        names.add("test2");
        names.add("test3");
        List<Employee> employees = employeeRepository.findByNameInOrAgeLessThan(names, 22);

        for (Employee employee : employees) {
            System.out.println("id:" + employee.getId() + " , name:" + employee.getName() + " ,age:" + employee.getAge());
        }
    }

    @Test
    public void testFindByNameInAndAgeLessThan() {
        List<String> names = new ArrayList<>();
        names.add("test1");
        names.add("test2");
        names.add("test3");
        List<Employee> employees = employeeRepository.findByNameInAndAgeLessThan(names, 22);

        for (Employee employee : employees) {
            System.out.println("id:" + employee.getId() + " , name:" + employee.getName() + " ,age:" + employee.getAge());
        }
    }

    @Test
    public void testGetEmployeeByMaxId() {
        Employee employee = employeeRepository.getEmployeeByMaxId();
        System.out.println("id:" + employee.getId() + " , name:" + employee.getName() + " ,age:" + employee.getAge());
    }

    @Test
    public void testQueryParams1() {
        List<Employee> employees = employeeRepository.queryParams1("Johnny", 20);
        for (Employee employee : employees) {
            System.out.println("id:" + employee.getId() + " , name:" + employee.getName() + " ,age:" + employee.getAge());
        }
    }

    @Test
    public void testQueryParams2() {
        List<Employee> employees = employeeRepository.queryParams2("Johnny", 20);
        for (Employee employee : employees) {
            System.out.println("id:" + employee.getId() + " , name:" + employee.getName() + " ,age:" + employee.getAge());
        }
    }

    @Test
    public void testQueryLike1() {
        List<Employee> employees = employeeRepository.queryLike1("test");
        for (Employee employee : employees) {
            System.out.println("id:" + employee.getId() + " , name:" + employee.getName() + " ,age:" + employee.getAge());
        }
    }

    @Test
    public void testQueryLike2() {
        List<Employee> employees = employeeRepository.queryLike2("test1");
        for (Employee employee : employees) {
            System.out.println("id:" + employee.getId() + " , name:" + employee.getName() + " ,age:" + employee.getAge());
        }
    }

    @Test
    public void testGetCount() {
        System.out.println("count:" + employeeRepository.getCount());
    }

}