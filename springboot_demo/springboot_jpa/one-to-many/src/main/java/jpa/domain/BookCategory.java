package jpa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "book_category")
@Data
@NoArgsConstructor
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(mappedBy = "bookCategory", cascade = CascadeType.ALL)
    private Set<Book> books;

    public BookCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(String.format(
                "Category[id=%d, name='%s']%n",
                id, name));
        if (books != null) {
            for (Book book : books) {
                result.append(String.format(
                        "Book[id=%d, name='%s']%n",
                        book.getId(), book.getName()));
            }
        }
        return result.toString();
    }

}