package neo4j.repository;

import neo4j.domain.Actor;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Johnny on 2018/3/17.
 */
@Repository
public interface ActorRepository extends GraphRepository<Actor> {
    @Query("MATCH (actor:ACTOR {name:{name}}) RETURN actor")
    Actor getActorByName(@Param("name") String name);
}