package servlet.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by Johnny on 2018/3/26.
 */
@Data
public class Employee {
    private int empNo;
    private String name;
    private String job;
    private int mgr;
    private Date hireDate;
    private int sal;
    private int comm;
    private int deptNo;
}