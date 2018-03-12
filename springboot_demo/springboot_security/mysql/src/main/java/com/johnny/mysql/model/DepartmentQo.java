package com.johnny.mysql.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentQo extends PageQo {
    private Long id;
    private String name;
}
