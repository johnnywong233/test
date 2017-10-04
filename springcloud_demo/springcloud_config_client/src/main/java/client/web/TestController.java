package client.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Johnny
 * Date: 2017/10/4
 * Time: 21:35
 */
@RestController
public class TestController {
    @Value("${profile}")
    private String profile;

    @GetMapping("/test")
    public String test() {
        return this.profile;
    }
}