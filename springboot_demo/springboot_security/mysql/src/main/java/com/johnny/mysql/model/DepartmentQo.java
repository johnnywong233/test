package com.johnny.mysql.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DepartmentQo extends PageQo {
    private Long id;
    private String name;
}
