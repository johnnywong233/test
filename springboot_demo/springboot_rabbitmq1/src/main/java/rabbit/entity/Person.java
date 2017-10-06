package rabbit.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:48
 */
@Data
public class Person implements Serializable {
    private String name;

    private Integer age;
}
