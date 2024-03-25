package neo4j.domain;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * Created by Johnny on 2018/3/17.
 */
@Data
@RelationshipProperties
public class Seen {
    @Id
    @GeneratedValue
    private Long id;
    @Property
    private Integer stars;
    @TargetNode
    private Actor startNode;
    @TargetNode
    private Movie endNode;

    public Seen(Integer stars, Actor startNode, Movie endNode) {
        this.stars = stars;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}
