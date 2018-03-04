package oauth2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Johnny on 2018/3/4.
 */
@Data
@AllArgsConstructor
public class User {
    private Integer userId;
    private String name;
    private String password;
}