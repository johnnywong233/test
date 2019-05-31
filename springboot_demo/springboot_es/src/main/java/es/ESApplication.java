package es;

import es.model.Book;
import es.service.BookService;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/9/24
 * Time: 17:17
 */
@SpringBootApplication
public class EsApplication implements CommandLineRunner {

    @Autowired
    private ElasticsearchOperations es;

    @Autowired
    private BookService bookService;

    @Override
    public void run(String... args) {

        printElasticSearchInfo();

        bookService.save(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
        bookService.save(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
        bookService.save(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));

        //fuzzy search
        Page<Book> books = bookService.findByAuthor("Rambabu", new PageRequest(0, 10));

        //List<Book> books = bookService.findByTitle("Elasticsearch Basics");

        books.forEach(System.out::println);
    }

    //useful for debug, print elastic search details
    private void printElasticSearchInfo() {
        System.out.println("--ElasticSearch--");
        Client client = es.getClient();
        Map<String, String> asMap = client.settings().getAsMap();
        asMap.forEach((k, v) -> System.out.println(k + " = " + v));
        System.out.println("--ElasticSearch--");
    }

    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class, args);
    }
}
