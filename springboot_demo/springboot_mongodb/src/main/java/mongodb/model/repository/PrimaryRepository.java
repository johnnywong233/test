package mongodb.model.repository;

import mongodb.model.PrimaryMongoObject;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 23:15
 */
public interface PrimaryRepository extends MongoRepository<PrimaryMongoObject, String> {
}
