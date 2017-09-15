package mongodb.model.repository;

import mongodb.model.SecondaryMongoObject;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 23:16
 */
public interface SecondaryRepository extends MongoRepository<SecondaryMongoObject, String> {
}
