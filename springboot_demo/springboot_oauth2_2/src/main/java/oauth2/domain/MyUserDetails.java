package oauth2.domain;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

/**
 * Created by Johnny on 2018/3/4.
 */
@Data
//自定义UserDetails类 携带User实例, 注意 User 是 security 包里面的
public class MyUserDetails extends User {
    private oauth2.domain.User user;

    public MyUserDetails(oauth2.domain.User user) {
        super(user.getName(), user.getPassword(), true, true, true, true, Collections.EMPTY_SET);
        this.user = user;
    }
}
