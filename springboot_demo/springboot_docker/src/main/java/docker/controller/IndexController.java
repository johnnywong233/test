package docker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: Johnny
 * Date: 2017/5/14
 * Time: 15:22
 */
@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    public String helloIndex(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Requested Session Id: " + request.getRequestedSessionId());
        logger.info("this is IndexController and helloIndex method {}.", request.getRequestedSessionId());
        return "index";
    }
}
