package oauth2.config;

import oauth2.domain.MyUserDetails;
import oauth2.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Johnny on 2018/3/4.
 */
@Primary
@Service("userService")
public class UserServiceImpl implements UserService {
    private final static Set<User> users = new HashSet<>();

    static {
        users.add(new User(1, "test-user1", "123451"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userWrapper = users.stream()
                .filter((u) -> u.getName().equals(s))
                .findFirst();
        if (!userWrapper.isPresent()) {
            throw new UsernameNotFoundException("there's no user founded!");
        } else {
            return new MyUserDetails(userWrapper.get());
        }

    }

}
