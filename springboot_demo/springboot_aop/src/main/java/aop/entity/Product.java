package aop.entity;

import aop.annotation.NeedSecured;
import lombok.Data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 14:24
 */
@NeedSecured
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String category;

    private String detail;

    private BigDecimal buyPrice;

    private BigDecimal sellPrice;

    private String provider;

    private Date onlineTime;

    private Date updateTime;
}
