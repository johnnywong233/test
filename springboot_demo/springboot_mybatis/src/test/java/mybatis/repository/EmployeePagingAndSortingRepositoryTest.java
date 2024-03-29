package mybatis.repository;

import mybatis.domain.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Author: Johnny
 * Date: 2017/9/19
 * Time: 21:00
 */
public class EmployeePagingAndSortingRepositoryTest {
    private ApplicationContext ctx = null;
    private EmployeePagingAndSortingRepository employeePagingAndSortingRepository = null;

    @BeforeEach
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("beans-new.xml");
        employeePagingAndSortingRepository = ctx.getBean(EmployeePagingAndSortingRepository.class);
        System.out.println("setup");
    }

    @AfterEach
    public void tearDown() {
        ctx = null;
        System.out.println("teardown");
    }

    @Test
    public void testPageAndSort() {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = Sort.by(order);

        Pageable pageable = PageRequest.of(0, 5, sort);
        Page<Employee> page = employeePagingAndSortingRepository.findAll(pageable);

        System.out.println("查询的总页数：" + page.getTotalPages());
        System.out.println("查询的总记录数：" + page.getTotalElements());
        System.out.println("查询当前页：" + page.getNumber() + 1);
        System.out.println("查询当前页面集合：" + page.getContent());
        System.out.println("查询当前页面记录数：" + page.getNumberOfElements());
    }

}