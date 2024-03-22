package mybatis.repository;

import jakarta.persistence.criteria.Path;
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
import org.springframework.data.jpa.domain.Specification;

/**
 * Author: Johnny
 * Date: 2017/9/19
 * Time: 21:34
 */
public class EmployeeJpaSpecificationExecutorRepositoryTest {

    private ApplicationContext ctx = null;

    private EmployeeJpaSpecificationExecutorRepository employeeJpaSpecificationExecutorRepository = null;

    @BeforeEach
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("beans-new.xml");
        employeeJpaSpecificationExecutorRepository = ctx.getBean(EmployeeJpaSpecificationExecutorRepository.class);
        System.out.println("setup");
    }

    @AfterEach
    public void tearDown() {
        ctx = null;
        System.out.println("tearDown");
    }

    /**
     * 先分页，再排序，继而查询
     */
    @Test
    public void query() {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = Sort.by(order);
        Pageable pageable = PageRequest.of(0, 5, sort);

        /*
         * root就是要查询的类型（Employee）
         * criteriaQuery，查询条件
         * criteriaBuilder，构建Predicate
         */
        Specification<Employee> specification = (root, criteriaQuery, criteriaBuilder) -> {
            Path<Integer> path = root.get("age");
            return criteriaBuilder.gt(path, 60);
        };
        Page<Employee> page = employeeJpaSpecificationExecutorRepository.findAll(specification, pageable);

        System.out.println("查询的总页数：" + page.getTotalPages());
        System.out.println("查询的总记录数：" + page.getTotalElements());
        System.out.println("查询当前页为：" + page.getNumber() + 1);
        System.out.println("查询当前页面集合：" + page.getContent());
        System.out.println("查询当前页面记录数：" + page.getNumberOfElements());
    }


}