package download.config;

import download.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 13:25
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static List<String> AUTH_IGNORED_PATH_LIST = new ArrayList<>();

    // config for auth ignored resources or path
    static{
        //ignore for static resources
        AUTH_IGNORED_PATH_LIST.add("/css/**");
        AUTH_IGNORED_PATH_LIST.add("/img/**");
        AUTH_IGNORED_PATH_LIST.add("/js/**");
        AUTH_IGNORED_PATH_LIST.add("/locale/**");
        AUTH_IGNORED_PATH_LIST.add("/favicon.ico");

        //IGNORE SWAGGER
        AUTH_IGNORED_PATH_LIST.add("/swagger-ui.html");
        AUTH_IGNORED_PATH_LIST.add("/webjars/springfox-swagger-ui/**");
        AUTH_IGNORED_PATH_LIST.add("/swagger-resources/**");
        AUTH_IGNORED_PATH_LIST.add("/v2/api-docs/**");
        AUTH_IGNORED_PATH_LIST.add("/configuration/**");
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        for(String path : AUTH_IGNORED_PATH_LIST){
            webSecurity.ignoring().antMatchers(path);
        }
    }

    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                //add remember me with cookie
//                .and().rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7)
                //set private key for cookie, CANNOT be null
//                .key("test")
                .and().logout().logoutSuccessUrl("/").permitAll();
    }
}
