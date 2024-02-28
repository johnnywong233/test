package angular.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Johnny
 * Date: 2017/9/20
 * Time: 22:13
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    @Id
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private Integer age;
}
