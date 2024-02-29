package neo4j.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
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
    @Id
    @GeneratedValue
    private Long nodeId;
    @Property(name = "name")
    private String name;
    //关系直接定义在节点中
    @Relationship(type = "IS_FRIEND_OF")
    private List<Actor> friends;
    //使用外部定义的关系
    @Relationship(type = "HAS_SEEN")
    private List<Seen> hasSeenMovies;

    public Actor(String name) {
        this.name = name;
    }
}