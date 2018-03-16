package webflux.domain;

import lombok.Data;

/**
 * Author: Johnny
 * Date: 2018/3/16
 * Time: 22:14
 */
@Data
public class User {
    private String id;
    private String name;
    private String email;
}