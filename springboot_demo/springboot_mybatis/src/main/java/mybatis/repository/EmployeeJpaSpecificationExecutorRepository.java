package mybatis.repository;

import mybatis.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Author: Johnny
 * Date: 2017/9/19
 * Time: 20:56
 */
public interface EmployeeJpaSpecificationExecutorRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
}