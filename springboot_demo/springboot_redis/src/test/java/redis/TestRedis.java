package redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.model.User;

import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2017/10/17
 * Time: 16:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
//TODO
@SpringApplicationConfiguration(App.class)
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception {
        User user = new User("aa@126.com", "aa");
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("com.johnny", user);
        operations.set("com.johnny.f", user, 1, TimeUnit.SECONDS);
        Thread.sleep(1000);
        //redisTemplate.delete("com.neo.f");
        boolean exists = redisTemplate.hasKey("com.neo.f");
        if (exists) {
            System.out.println("exists is true");
        } else {
            System.out.println("exists is false");
        }
         Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }
}
