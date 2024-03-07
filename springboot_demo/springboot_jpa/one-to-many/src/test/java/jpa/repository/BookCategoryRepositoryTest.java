package jpa.repository;

import jpa.domain.Book;
import jpa.domain.BookCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BookCategoryRepositoryTest {

    @Autowired
    private BookCategoryRepository repository;


    @Test
    public void saveCategoryTest() {
        BookCategory categoryOne = new BookCategory("Category One");
        Set<Book> books = new HashSet<>() {{
            add(new Book("Book One", categoryOne));
            add(new Book("Book Two", categoryOne));
            add(new Book("Book Three", categoryOne));
        }};
        categoryOne.setBooks(books);

        BookCategory categoryTwo = new BookCategory("Category Two");
        Set<Book> bookBs = new HashSet<>() {{
            add(new Book("Book Four", categoryTwo));
            add(new Book("Book Five", categoryTwo));
            add(new Book("Book Six", categoryTwo));
        }};
        categoryTwo.setBooks(bookBs);

        Set<BookCategory> allBooks = new HashSet<>();

        allBooks.add(categoryOne);
        allBooks.add(categoryTwo);

        List list = repository.save(allBooks);

        Assert.assertNotNull(list);
    }

    @Test
    public void findAll() {

        for (BookCategory bookCategory : repository.findAll()) {
            log.info(bookCategory.toString());
        }
    }
}