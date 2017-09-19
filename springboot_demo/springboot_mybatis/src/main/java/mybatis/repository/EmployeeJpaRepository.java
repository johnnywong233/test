package mybatis.repository;

import mybatis.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Johnny
 * Date: 2017/9/19
 * Time: 21:28
 */
public interface EmployeeJpaRepository extends JpaRepository<Employee, Integer> {
}
