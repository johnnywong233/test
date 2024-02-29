package mongodb.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 18:37
 */
@Data
public class UserEntity implements Serializable {
    private Long id;
    private String userName;
    private String passWord;
}
