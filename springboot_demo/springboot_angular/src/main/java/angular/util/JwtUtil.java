package angular.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/10/4
 * Time: 0:53
 */
@Slf4j
public class JwtUtil {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String USER_ID = "userId";
    private static final Long EXPIRATION_TIME = 3600000L; // 1 hour
    private static final String SECRET = "ThisIsASecretThisIsASecretThisIsASecretThisIsASecret";

    public static String generateToken(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        //put any data in the map
        try {
            map.put(USER_ID, EncryptUtil.encrypt(userId));
        } catch (Exception e) {
            log.warn("Encryption failed.", e);
            throw new RuntimeException("Encryption failed");
        }
        return generateToken(map);
    }

    public static String generateToken(Map<String, Object> claims) {
        SecretKey secret = Jwts.SIG.HS512.key().build();
        return Jwts.builder()
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secret)
                .compact();
    }
    public static HttpServletRequest validateTokenAndAddUserIdToHeader(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            try {
                SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
                Map<String, Object> body = Jwts.parser()
                        .verifyWith(secret)
                        .build()
                        .parseSignedClaims(token.replace(TOKEN_PREFIX, ""))
                        .getPayload();
                String userId = (String) body.get(USER_ID);
                return new CustomHttpServletRequest(request, EncryptUtil.decrypt(userId));
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new TokenValidationException(e.getMessage());
            }
        } else {
            log.info("Missing token");
            throw new TokenValidationException("Missing token");
        }
    }

    public static class CustomHttpServletRequest extends HttpServletRequestWrapper {
        private final String userId;

        CustomHttpServletRequest(HttpServletRequest request, String userId) {
            super(request);
            this.userId = userId;
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (name != null && (name.equals(USER_ID))) {
                return Collections.enumeration(Collections.singletonList(userId));
            }
            return super.getHeaders(name);
        }
    }

    static class TokenValidationException extends RuntimeException {
        TokenValidationException(String msg) {
            super(msg);
        }
    }
}