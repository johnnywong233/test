package redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


/**
 * Author: Johnny
 * Date: 2017/4/25
 * Time: 20:05
 */
@Configuration
//must have at least one session annotation to make auto configure work
//@EnableRedisHttpSession  //启动redis保存session状态.
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30) //30s失效
public class RedisSessionConfig {


    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connection = new JedisConnectionFactory();
        connection.setHostName("127.0.0.1");
        connection.setPort(6379);
        return connection;
    }
}
