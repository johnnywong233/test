package mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 23:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "first_mongo")
public class PrimaryMongoObject {
    @Id
    private String id;

    private String value;

    public PrimaryMongoObject(String id, String value) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PrimaryMongoObject{" + "id='" + id + '\'' + ", value='" + value + '\'' + '}';
    }
}