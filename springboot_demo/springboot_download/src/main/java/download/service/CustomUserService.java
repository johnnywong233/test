package download.service;

import download.domain.SysUserRepository;
import download.domain.entity.SysUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 不能添加类注解 Configuration
 *
 * @author Johnny
 * Date: 2017/7/13
 * Time: 13:26
 */
@Slf4j
public class CustomUserService implements UserDetailsService {
    @Resource
    private SysUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("loadUserByUsername failed due to user not existed!");
            throw new UsernameNotFoundException("user does not exist!");
        }
        log.info("Input username is [{}]; SysUser is [{}]; and password is *****.", username, user.getUsername());
        return user;
    }
}
