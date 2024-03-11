package angular.web;

import angular.entity.AccountCredentials;
import angular.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static angular.util.JwtUtil.HEADER_STRING;
import static angular.util.JwtUtil.TOKEN_PREFIX;
import static angular.util.JwtUtil.USER_ID;

/**
 * Author: Johnny
 * Date: 2017/10/4
 * Time: 13:01
 */
@Slf4j
@RestController
public class LoginController {
    //Missing token
    @GetMapping("/api/protected")
    public Object hellWorld(@RequestHeader(value = USER_ID) String userId) {
        log.info("This is a protected api, your use id is " + userId);
        return "Hello World! This is a protected api, your use id is " + userId;
    }

    @PostMapping("/login")
    public String login(HttpServletResponse response, @RequestBody final AccountCredentials credentials) throws IOException {
        //here we just have one hardcoded username=admin and password=admin
        //add user validation code here
        if (validCredentials(credentials)) {
            String jwt = JwtUtil.generateToken(credentials.getUsername());
            log.info("jwt:{}", jwt);
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
            return jwt;
        } else {
            log.warn("You have provided a invalid credentials! ");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Wrong credentials");
            return "";
        }
    }

    private boolean validCredentials(AccountCredentials credentials) {
        //hard code
        return "admin".equals(credentials.getUsername()) && "admin".equals(credentials.getPassword());
    }
}
