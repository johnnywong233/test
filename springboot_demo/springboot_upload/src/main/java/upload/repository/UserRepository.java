package upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upload.entity.User;

/**
 * Author: Johnny
 * Date: 2017/9/30
 * Time: 16:30
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    Long deleteById(Long id);
}
