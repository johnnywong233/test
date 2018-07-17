package entity;

import lombok.AllArgsConstructor;

/**
 * Created by Johnny on 2018/3/3.
 */
@AllArgsConstructor
public class Student implements Person {
    private String studentId;
    private String name;
    private String able;

    @Override
    public String getId() {
        return studentId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String able() {
        return able;
    }
}
