package mongodb.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: Johnny
 * Date: 2017/10/8
 * Time: 17:14
 */
@Data
public class JwtAuthenticationRequest implements Serializable {
	private String username;
    private String password;
}
