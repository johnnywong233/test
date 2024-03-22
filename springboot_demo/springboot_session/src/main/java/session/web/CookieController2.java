package session.web;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 18:25
 */
@Controller
public class CookieController2 {
    @Resource
    private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @RequestMapping("/test/findByUsername")
    public Map<String, ?> findByUsername(@RequestParam String username) {
        return sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
    }
}
