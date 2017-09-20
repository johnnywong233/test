package angular.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Author: Johnny
 * Date: 2017/9/20
 * Time: 22:13
 */
@Entity
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private Integer age;
    public Student() {
    }
    public Student(long id, String name, String gender, Integer age) {
        super();
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + '\'' + ", gender='" + gender + '\'' + ", age=" + age + '}';
    }
}
