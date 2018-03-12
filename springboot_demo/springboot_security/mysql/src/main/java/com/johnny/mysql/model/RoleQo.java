package com.johnny.mysql.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RoleQo extends PageQo {
    private Long id;
    private String name;
}