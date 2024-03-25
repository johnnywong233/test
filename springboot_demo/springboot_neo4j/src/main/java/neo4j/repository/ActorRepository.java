package neo4j.repository;

import neo4j.domain.Actor;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Johnny on 2018/3/17.
 */
@Repository
public interface ActorRepository extends Neo4jRepository<Actor, Long> {
    @Query("MATCH (actor:ACTOR {name:{name}}) RETURN actor")
    Actor getActorByName(@Param("name") String name);
}