package neo4j.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Created by Johnny on 2018/3/17.
 */
@Data
@NodeEntity(label = "Actor")
@NoArgsConstructor
public class Actor {
    @GraphId
    private Long nodeId;
    @Property(name = "name")
    private String name;
    //关系直接定义在节点中
    @Relationship(type = "IS_FRIEND_OF", direction = Relationship.OUTGOING)
    private List<Actor> friends;
    //使用外部定义的关系
    @Relationship(type = "HAS_SEEN")
    private List<Seen> hasSeenMovies;

    public Actor(String name) {
        this.name = name;
    }
}