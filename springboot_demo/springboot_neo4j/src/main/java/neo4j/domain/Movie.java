package neo4j.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Created by Johnny on 2018/3/17.
 */
@Data
@NodeEntity(label = "MOVIES")
@NoArgsConstructor
public class Movie {
    @GraphId
    private Long nodeId;
    @Property(name = "name")
    private String name;
    public Movie(String name) {
        this.name = name;
    }
}