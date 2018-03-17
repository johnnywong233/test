package neo4j.repository;

import neo4j.domain.Seen;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Johnny on 2018/3/17.
 */
@Repository
public interface SeenRepository extends GraphRepository<Seen> {
}