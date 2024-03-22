package es.web;

import es.model.Book;
import es.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author: Johnny
 * Date: 2017/10/3
 * Time: 14:52
 */
@Controller
public class BookController {
    @Resource
    private BookService bookService;

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String test() {
        Book book = new Book("1", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
        bookService.save(book);
        Book findBook = bookService.findOne("1");
        System.out.println(findBook.getAuthor());
        return "";
    }


}
