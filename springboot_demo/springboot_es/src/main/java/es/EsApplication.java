package es;

import es.model.Book;
import es.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/9/24
 * Time: 17:17
 */
@Slf4j
@SpringBootApplication
public class EsApplication implements CommandLineRunner {
    @Resource
    private ElasticsearchOperations es;
    @Resource
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class, args);
    }

    @Override
    public void run(String... args) {

        printElasticSearchInfo();

        bookService.save(new Book("1001", "Elasticsearch Basics", "George Orwell", "23-FEB-2017"));
        bookService.save(new Book("1002", "Apache Lucene Basics", "George Orwell", "13-MAR-2017"));
        bookService.save(new Book("1003", "Apache Solr Basics", "George Orwell", "21-MAR-2017"));

        //fuzzy search
        Page<Book> books = bookService.findByAuthor("Orwell", PageRequest.of(0, 10));

        //List<Book> books = bookService.findByTitle("Elasticsearch Basics");

        books.forEach(x -> log.info("book:{}", x));
    }

    //useful for debug, print elastic search details
    private void printElasticSearchInfo() {
        Client client = es.getClient();
        Map<String, Settings> asMap = client.settings().getAsGroups();
        asMap.forEach((k, v) -> log.info(k + " = " + v));
    }
}
