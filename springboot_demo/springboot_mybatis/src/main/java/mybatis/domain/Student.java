package mybatis.domain;

import lombok.Data;

/**
 * Author: Johnny
 * Date: 2017/9/17
 * Time: 15:50
 */
@Data
public class Student {
    /**
     * 主键字段
     */
    private int id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private int age;
}
