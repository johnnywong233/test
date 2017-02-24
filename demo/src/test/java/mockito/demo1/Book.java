package mockito.demo1;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/2/23
 * Time: 9:57
 * Model class for the book details.
 */
public class Book {
    private String isbn;
    private String title;
    private List<String> authors;
    private String publication;
    private Integer yearOfPublication;
    private Integer numberOfPages;
    private String image;

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
