package stream.bean;

import lombok.Data;

import java.util.Date;

/**
 * Author: Johnny
 * Date: 2017/11/8
 * Time: 23:52
 */
@Data
public class Order {
    private String orderNum;

    private String type;

    private int num;

    private Date createAt;
}
