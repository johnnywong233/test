package jpa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @ManyToMany(cascade = CascadeType.MERGE)
    //
    @JoinTable(name = "book_publisher", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id", referencedColumnName = "id"))
    private Set<Publisher> publishers = new HashSet<>();

    public Book(String name) {
        this.name = name;
    }

    public Book(String name, Set<Publisher> publishers) {
        this.name = name;
        this.publishers = publishers;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(String.format(
                "Book [id=%d, name='%s']%n",
                id, name));
        if (publishers != null) {
            for (Publisher publisher : publishers) {
                result.append(String.format(
                        "Publisher[id=%d, name='%s']%n",
                        publisher.getId(), publisher.getName()));
            }
        }
        return result.toString();
    }
}
