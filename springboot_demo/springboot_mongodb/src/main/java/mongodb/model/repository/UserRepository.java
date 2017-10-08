package mongodb.model.repository;

import mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Johnny
 * Date: 2017/10/8
 * Time: 17:09
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(final String username);
}
