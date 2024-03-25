package upload.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Author: Johnny
 * Date: 2017/9/30
 * Time: 16:28
 */
@Entity
@Accessors(chain = true)
@Data
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int age;
}
