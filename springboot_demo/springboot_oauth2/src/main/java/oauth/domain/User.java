package oauth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author: Johnny
 * Date: 2017/5/1
 * Time: 23:12
 */
@Data
@AllArgsConstructor
public class User {
    private String name;
    private Integer age;
}