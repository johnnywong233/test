package neo4j.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

/**
 * Created by Johnny on 2018/3/17.
 */
@Data
@Node(value = "Actor")
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