package es.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Author: Johnny
 * Date: 2017/9/24
 * Time: 17:21
 */
//注意index需要小写:Invalid index name [Book], must be lowercase
@Document(indexName = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String releaseDate;
}
