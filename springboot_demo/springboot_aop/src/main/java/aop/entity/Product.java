package aop.entity;

import aop.annotation.DataLog;
import aop.annotation.NeedSecured;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @DataLog(name = "产品名称")
    private String name;

    private String category;

    private String detail;

    private BigDecimal buyPrice;

    private BigDecimal sellPrice;

    private String provider;

    private Date onlineTime;

    private Date updateTime;
}
