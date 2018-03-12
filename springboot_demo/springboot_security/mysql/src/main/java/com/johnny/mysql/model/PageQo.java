package com.johnny.mysql.model;

import lombok.Data;

@Data
public class PageQo {
    private Integer page = 0;
    private Integer size = 10;
}
