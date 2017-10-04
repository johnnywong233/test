package demo1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    //不同的RequestMethod，返回不一样的内容/页面/响应
    @RequestMapping(value = "/login1", method = RequestMethod.GET)
    public String loginGet() {
        return "Login Page";
    }

    @RequestMapping(value = "/login1", method = RequestMethod.POST)
    public String loginPost() {
        return "Login Post Request";
    }
}
