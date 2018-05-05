package pattern.build.case1;

import lombok.Data;

/**
 * Author: Johnny
 * Date: 2018/5/6
 * Time: 0:21
 */
@Data
public class Person {
    private String head;
    private String body;
    private String foot;
}

class Woman extends Person {
    public Woman() {
        System.out.println("开始建造女人");
    }
}
