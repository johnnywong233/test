package angular.web;

import angular.entity.AccountCredentials;
import angular.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    //Missing token
    @GetMapping("/api/protected")
    @ResponseBody
    public Object hellWorld(@RequestHeader(value = USER_ID) String userId) {
        logger.info("This is a protected api, your use id is " + userId);
        return "Hello World! This is a protected api, your use id is " + userId;
    }

    @PostMapping("/login")
    public void login(HttpServletResponse response, @RequestBody final AccountCredentials credentials) throws IOException {
        //here we just have one hardcoded username=admin and password=admin
        //add user validation code here
        if (validCredentials(credentials)) {
            String jwt = JwtUtil.generateToken(credentials.username);
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
        } else {
            logger.warn("You have provided a invalid credentials! ");
        }
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Wrong credentials");
    }

    private boolean validCredentials(AccountCredentials credentials) {
        //hard code
        return "admin".equals(credentials.username) && "admin".equals(credentials.password);
    }
}
