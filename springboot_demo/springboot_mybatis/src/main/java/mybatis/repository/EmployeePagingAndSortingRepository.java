package mybatis.repository;

import mybatis.domain.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Author: Johnny
 * Date: 2017/9/19
 * Time: 20:59
 */
public interface EmployeePagingAndSortingRepository extends PagingAndSortingRepository<Employee, Integer> {
}