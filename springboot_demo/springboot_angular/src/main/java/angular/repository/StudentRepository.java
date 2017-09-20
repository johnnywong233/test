package angular.repository;

import angular.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Johnny
 * Date: 2017/9/20
 * Time: 22:12
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
}
