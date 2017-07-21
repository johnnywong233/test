package download.service;

import download.domain.SysUserRepository;
import download.domain.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 13:26
 */
public class CustomUserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(CustomUserService.class);

    @Autowired
    SysUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("loadUserByUsername failed due to user not existed!");
            throw new UsernameNotFoundException("user does not exist!");
        }
        logger.info("Input username [{}]; SysUser [{}]; and password [{}].", username, user.getUsername(), user.getPassword());
        return user;
    }
}
