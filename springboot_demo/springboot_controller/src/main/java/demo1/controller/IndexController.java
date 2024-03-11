package demo1.controller;

import demo1.entity.AccountCredentials;
import demo1.util.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static demo1.util.JwtUtil.HEADER_STRING;
import static demo1.util.JwtUtil.TOKEN_PREFIX;

@RestController
public class IndexController {

    //仅支持GET，不写方法的话，HTTP所有 METHOD都支持
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String test() {
        return "Get Page";
    }

    //支持GET和POST
    //添加params之后需要在URL里面添加如下"?test"，否则报错：
    // Parameter conditions "test" not met for actual request parameters:
    //https://localhost:8443/hello?test
    @RequestMapping(value = "/hello", params = "test", method = {RequestMethod.POST, RequestMethod.GET})
//    @RequestMapping(value = "/hello", method = {RequestMethod.POST, RequestMethod.GET})
    public String hello() {
        return "Hello World!";
    }

    //Missing token
    @GetMapping("/api/protected")
    public Object hellWorld() {
        return "Hello World! This is a protected api";
    }

    @PostMapping("/login")
    public void login(HttpServletResponse response, @RequestBody final AccountCredentials credentials) throws IOException {
        //here we just have one hardcoded username=admin and password=admin
        //add user validation code here
        if (validCredentials(credentials)) {
            String jwt = JwtUtil.generateToken(credentials.getUsername());
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Wrong credentials");
        }
    }

    private boolean validCredentials(AccountCredentials credentials) {
        return "admin".equals(credentials.getUsername()) && "admin".equals(credentials.getPassword());
    }

}
