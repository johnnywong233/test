package redis;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.model.User;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2017/10/17
 * Time: 16:44
 */
public class TestRedis {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Test
    public void test() {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assertions.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception {
        User user = new User("aa@126.com", "aa");
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("com.johnny", user);
        operations.set("com.johnny.f", user, 1, TimeUnit.SECONDS);
        Thread.sleep(1000);
        //redisTemplate.delete("com.neo.f");
        Assertions.assertEquals(true, redisTemplate.hasKey("com.neo.f"));
        Assertions.assertEquals("aa", Objects.requireNonNull(operations.get("com.neo.f")).getUserName());
    }
}
