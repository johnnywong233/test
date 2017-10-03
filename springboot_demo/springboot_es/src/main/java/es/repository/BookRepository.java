package es.repository;

import es.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/9/24
 * Time: 17:22
 */
public interface BookRepository extends ElasticsearchRepository<Book, String> {
    Page<Book> findByAuthor(String author, Pageable pageable);

    List<Book> findByTitle(String title);
}
