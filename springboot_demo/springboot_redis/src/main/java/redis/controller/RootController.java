package redis.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/4/25
 * Time: 20:05
 */
@Controller
public class RootController {

    //多个url指向同一个页面的示例：
    @RequestMapping({"/", "/index"})
    public String index(HttpServletRequest request) {
        System.out.println("request session id: " +request.getSession().getId());
        return "index";
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("map"));
        return map;
    }
}
