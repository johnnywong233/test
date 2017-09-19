package mybatis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Author: Johnny
 * Date: 2017/9/17
 * Time: 15:51
 * 雇员:  先开发实体类===>自动生成数据表
 */
@Entity
public class Employee {
    private Integer id;

    private String name;

    private Integer age;

    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //by default, if not define length, the length will be 256; other file such as NotNull can be defined here too
    @Column(length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id + ", name='" + name + '\'' +
                ", age=" + age + '}';
    }
}
