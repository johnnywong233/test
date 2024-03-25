package jpa.repository;

import jpa.domain.Book;
import jpa.domain.Publisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void saveTest() {

        Publisher publisherA = new Publisher("Publisher One");
        Publisher publisherB = new Publisher("Publisher Two");

        Book bookA = new Book("Book One");
        bookA.setPublishers(new HashSet<>() {{
            add(publisherA);
            add(publisherB);
        }});

        bookRepository.save(bookA);

    }

    @Test
    public void saveTest1() {
        Publisher publisher = publisherRepository.findById(24).orElseThrow();
        Book bookA = new Book("Book Two");
        bookA.getPublishers().add(publisher);
        bookRepository.save(bookA);
    }

    @Test
    public void saveTest2() {
        Book two = bookRepository.findById(18).orElseThrow();
        Publisher publisher = publisherRepository.findById(25).orElseThrow();
        two.getPublishers().add(publisher);
        bookRepository.save(two);
    }

    @Test
    public void findPublisherTest() {
        Publisher publisher = publisherRepository.findById(24).orElseThrow();
        Set<Book> books = publisher.getBooks();
        for (Book book : books) {
            log.info(book.getName() + "..." + book.getId());
        }
        Assert.assertNotNull(publisher);
        Assert.assertNotNull(publisher.getName());
    }

    @Test
    public void findAllTest() {
        for (Book book : bookRepository.findAll()) {
            log.info(book.toString());
        }
    }

    @Test
    public void findBookTest() {
        Book book = bookRepository.findById(16).orElseThrow();
        Set<Publisher> publishers = book.getPublishers();
        for (Publisher publisher : publishers) {
            log.info(publisher.getName() + "..." + publisher.getId());
        }
        Assert.assertNotNull(book);
        Assert.assertNotNull(book.getName());
    }
}