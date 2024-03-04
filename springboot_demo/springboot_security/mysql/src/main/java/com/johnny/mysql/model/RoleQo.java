package com.johnny.mysql.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleQo extends PageQo {
    private Long id;
    private String name;
}