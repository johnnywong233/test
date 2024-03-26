package mockito.demo1;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/2/23
 * Time: 9:57
 * Model class for the book details.
 */
public class Book {
    private final String isbn;
    private final String title;
    private final List<String> authors;
    private final String publication;
    private final Integer yearOfPublication;
    private final Integer numberOfPages;
    private final String image;

    Book(String isbn,
         String title,
         List<String> authors,
         String publication,
         Integer yearOfPublication,
         Integer numberOfPages,
         String image) {

        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publication = publication;
        this.yearOfPublication = yearOfPublication;
        this.numberOfPages = numberOfPages;
        this.image = image;

    }

    String getIsbn() {
        return isbn;
    }

    String getTitle() {
        return title;
    }

    List<String> getAuthors() {
        return authors;
    }

    String getPublication() {
        return publication;
    }

    Integer getYearOfPublication() {
        return yearOfPublication;
    }

    Integer getNumberOfPages() {
        return numberOfPages;
    }

    String getImage() {
        return image;
    }
}
