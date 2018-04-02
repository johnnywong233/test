package java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Johnny
 * Date: 2017/8/24
 * Time: 20:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String name;
    private int age;
    private String grade;

    public void sayHello() {
        System.out.println(this.id + " " + this.name);
    }
}
