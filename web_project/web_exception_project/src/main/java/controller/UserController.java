package controller;

import com.alibaba.druid.support.json.JSONUtils;
import errorcode.ErrorCode;
import exception.BusinessException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import util.DecriptUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @RequestMapping("/index.jhtml")
    public ModelAndView getIndex() {
        return new ModelAndView("index");
    }

    @RequestMapping("/exceptionForPageJumps.jhtml")
    public ModelAndView exceptionForPageJumps() {
        throw new BusinessException(ErrorCode.NULL_OBJ);
    }

    @RequestMapping(value = "/businessException.json", method = RequestMethod.POST)
    public String businessException() {
        throw new BusinessException(ErrorCode.NULL_OBJ);
    }

    @RequestMapping(value = "/otherException.json", method = RequestMethod.POST)
    public String otherException() throws Exception {
        throw new Exception();
    }

    //redirect to login page
    @RequestMapping("/login.jhtml")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    //redirect to log in success page
    @RequestMapping("/loginsuccess.jhtml")
    public ModelAndView loginsuccess() {
        return new ModelAndView("loginsuccess");
    }

    @RequestMapping("/newPage.jhtml")
    public ModelAndView newPage() {
        return new ModelAndView("newPage");
    }

    @RequestMapping("/newPageNotAdd.jhtml")
    public ModelAndView newPageNotAdd() {
        return new ModelAndView("newPageNotAdd");
    }

    //check username/password
    @RequestMapping(value = "/checkLogin.json", method = RequestMethod.POST)
    public String checkLogin(String username, String password) {
        Map<String, Object> result = new HashMap<>();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, DecriptUtil.md5(password));
            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()) {
                //through shiro
                token.setRememberMe(true);
                currentUser.login(token);
            }
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.LOGIN_VERIFY_FAILURE);
        }
        result.put("success", true);
        return JSONUtils.toJSONString(result);
    }

    //logout
    @RequestMapping(value = "/logout.json", method = RequestMethod.POST)
    public String logout() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return JSONUtils.toJSONString(result);
    }
}
