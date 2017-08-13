package oauth.controller;

import oauth.domain.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/5/1
 * Time: 21:53
 */
@RestController
@RequestMapping("/")
public class UserController {
    /**
     * list(find) all users
     */
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_USER')")//only user has this role can call this method
    public ModelAndView list(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("johnny", 29));
        list.add(new User("wong", 30));
        model.addAttribute("title", "user management");
        model.addAttribute("userList", list);
        return new ModelAndView("users/list", "userModel", model);
    }
}
