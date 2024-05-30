package es.service;

import es.EsApplication;
import es.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Author: Johnny
 * Date: 2017/9/24
 * Time: 17:32
 */
@SpringBootTest(classes = EsApplication.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @BeforeEach
    public void setUp() {
        esTemplate.delete(Book.class);
        esTemplate.createIndex(Book.class);
        esTemplate.putMapping(Book.class);
        esTemplate.refresh(Book.class);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSave() {
        Book book = new Book("1001", "Elasticsearch Basics", "wong", "23-FEB-2017");
        Book testBook = bookService.save(book);

        assertNotNull(testBook.getId());
        assertEquals(testBook.getTitle(), book.getTitle());
        assertEquals(testBook.getAuthor(), book.getAuthor());
        assertEquals(testBook.getReleaseDate(), book.getReleaseDate());
    }

    @Test
    public void testDelete() {
        Book book = new Book("1001", "Elasticsearch Basics", "wong", "23-FEB-2017");
        bookService.save(book);
        bookService.delete(book);
        Book testBook = bookService.findOne(book.getId());
        assertNull(testBook);
    }

    @Test
    public void testFindOne() {
        Book book = new Book("1001", "Elasticsearch Basics", "wong", "23-FEB-2017");
        bookService.save(book);

        Book testBook = bookService.findOne(book.getId());

        assertNotNull(testBook.getId());
        assertEquals(testBook.getTitle(), book.getTitle());
        assertEquals(testBook.getAuthor(), book.getAuthor());
        assertEquals(testBook.getReleaseDate(), book.getReleaseDate());
    }

    @Test
    public void findAll() {
    }

    @Test
    public void testFindByAuthor() {
        List<Book> bookList = new ArrayList<>();

        bookList.add(new Book("1001", "Elasticsearch Basics", "johnny", "23-FEB-2017"));
        bookList.add(new Book("1002", "Apache Lucene Basics", "wong", "13-MAR-2017"));
        bookList.add(new Book("1003", "Apache Solr Basics", "wong", "21-MAR-2017"));
        bookList.add(new Book("1007", "Spring Data + ElasticSearch", "wong", "01-APR-2017"));
        bookList.add(new Book("1008", "Spring Boot + MongoDB", "johnny", "25-FEB-2017"));

        for (Book book : bookList) {
            bookService.save(book);
        }

        Page<Book> byAuthor = bookService.findByAuthor("wong", PageRequest.of(0, 10));
        assertThat(byAuthor.getTotalElements(), is(4L));

        Page<Book> byAuthor2 = bookService.findByAuthor("johnny", PageRequest.of(0, 10));
        assertThat(byAuthor2.getTotalElements(), is(1L));

    }

    @Test
    public void findByTitle() {
        Book book = new Book("1001", "Elasticsearch Basics", "wong", "23-FEB-2017");
        bookService.save(book);

        List<Book> byTitle = bookService.findByTitle(book.getTitle());
        assertThat(byTitle.size(), is(1));
    }

}