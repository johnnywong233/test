package neo4j.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

/**
 * Created by Johnny on 2018/3/17.
 */
@Data
@Node(value = "MOVIES")
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue
    private Long nodeId;
    @Property(name = "name")
    private String name;

    public Movie(String name) {
        this.name = name;
    }
}