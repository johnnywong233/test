package oauth2.web;

import oauth2.domain.MyUserDetails;
import oauth2.domain.User;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/5/1
 * Time: 21:53
 */
@RestController
public class UserController {
    @Resource
    private TokenStore tokenStore;

    @PostMapping("/house")
    public String bar(@RequestHeader("Authorization") String auth) {
        MyUserDetails userDetails = (MyUserDetails) tokenStore.readAuthentication(auth.split(" ")[1]).getPrincipal();
        User user = userDetails.getUser();
        return user.getName() + ":" + user.getPassword();
    }
}
