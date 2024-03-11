package demo1.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/10/4
 * Time: 0:53
 */
public class JwtUtil {
    private static final Long EXPIRATION_TIME = 3600000L; // 1 hour
    private static final String SECRET = "ThisIsASecret";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static String generateToken(String username) {
        HashMap<String, Object> map = new HashMap<>();
        // you can put any data in the map
        map.put("username", username);
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        return Jwts.builder()
                .claims(map)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secret)
                .compact();
    }

    public static void validateToken(String token) {
        if (token != null) {
            SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
            Map<String, Object> body = Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token.replace(TOKEN_PREFIX, ""))
                    .getPayload();
            String username = (String) (body.get("username"));
            if (username == null || username.isEmpty()) {
                throw new TokenValidationException("Wrong token without username");
            }
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
