package demo1.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/10/4
 * Time: 0:53
 */
public class JwtUtil {
    private static final long EXPIRATION_TIME = 3600_000; // 1 hour
    private static final String SECRET = "ThisIsASecret";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    public static String generateToken(String username) {
        HashMap<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put("username", username);
        return Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static String validateToken(String token) {
        if (token != null) {
            // parse the token.
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String username = (String) (body.get("username"));
            if (username == null || username.isEmpty())
                throw new TokenValidationException("Wrong token without username");
            else
                return username;
        } else {
            throw new TokenValidationException("Missing token");
        }
    }

    static class TokenValidationException extends RuntimeException {
        TokenValidationException(String msg) {
            super(msg);
        }
    }
}
