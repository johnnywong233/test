package com.johnny.validate.model;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Book {
    private long id;

    /**
     * 书名
     */
    @NotEmpty(message = "书名不能为空")
    private String bookName;
    /**
     * ISBN号
     */
    @NotNull(message = "ISBN号不能为空")
    private String bookIsbn;
    /**
     * 单价
     */
    @DecimalMin(value = "0.1")
    private double price;
    /**
     * 出版日期
     */
    private Date publicationTime;
}