package redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author: Johnny
 * Date: 2017/11/22
 * Time: 14:52
 */
@Data
@AllArgsConstructor
public class User {
    private String mail;
    private String userName;
}