package mybatis.domain;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Author: Johnny
 * Date: 2017/9/17
 * Time: 15:51
 * 雇员:  先开发实体类===>自动生成数据表
 */
@Entity
@Data
public class Employee {
    private Integer id;

    private String name;

    private Integer age;

    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    //by default, if not define length, the length will be 256; other file such as NotNull can be defined here too
    @Column(length = 20)
    public String getName() {
        return name;
    }
}
