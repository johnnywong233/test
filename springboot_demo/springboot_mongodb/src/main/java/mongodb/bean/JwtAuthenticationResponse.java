package mongodb.bean;

import java.io.Serializable;

/**
 * Author: Johnny
 * Date: 2017/10/8
 * Time: 17:14
 */
public class JwtAuthenticationResponse implements Serializable {
    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
