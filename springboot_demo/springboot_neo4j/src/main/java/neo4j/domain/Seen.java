package neo4j.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Created by Johnny on 2018/3/17.
 */
@Data
@RelationshipEntity(type = "HAS_SEEN")
public class Seen {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private Integer stars;
    @StartNode
    private Actor startNode;
    @EndNode
    private Movie endNode;

    public Seen(Integer stars, Actor startNode, Movie endNode) {
        this.stars = stars;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}
