package docker.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Johnny
 * Date: 2017/5/14
 * Time: 15:22
 */
@Slf4j
@Controller
public class IndexController {
    @RequestMapping("/")
    public String helloIndex(HttpServletRequest request) {
        log.info("Requested Session Id: " + request.getRequestedSessionId());
        log.info("this is IndexController and helloIndex method {}.", request.getRequestedSessionId());
        return "index";
    }
}
