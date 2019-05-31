package com.johnny.drools.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by Johnny on 2018/4/6.
 */
@Data
public class Order {
    private Date bookingDate;//下单日期

    private int amount;//订单原价金额

    private User user;//下单人

    private int score;

}
