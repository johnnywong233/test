package jpa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @ManyToMany(mappedBy = "publishers")
    private Set<Book> books = new HashSet<>();

    public Publisher(String name) {
        this.name = name;
    }

    public Publisher(String name, Set<Book> books) {
        this.name = name;
        this.books = books;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(String.format(
                "Publisher [id=%d, name='%s']%n",
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
