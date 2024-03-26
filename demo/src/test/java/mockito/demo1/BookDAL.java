package mockito.demo1;

import java.util.Collections;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/2/23
 * Time: 9:58
 * API layer for persisting and retrieving the Book objects.
 */
public class BookDAL {

    private static final BookDAL bookDAL = new BookDAL();

    List<Book> getAllBooks() {
        return Collections.emptyList();
    }

    Book getBook(String isbn) {
        return null;
    }

    String addBook(Book book) {
        return book.getIsbn();
    }

    String updateBook(Book book) {
        return book.getIsbn();
    }

    public static BookDAL getInstance() {
        return bookDAL;
    }
}
