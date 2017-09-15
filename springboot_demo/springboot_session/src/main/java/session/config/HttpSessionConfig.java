package session.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 18:26
 */
@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {
}
