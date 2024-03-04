package test;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by johnny on 2016/8/13.
 */
@Data
public class Employee implements Serializable {
    private static final long serialVersionUID = 7987370668570908602L;
    public String name;
    public String address;
    public transient int ssn;
    public int number;
}
