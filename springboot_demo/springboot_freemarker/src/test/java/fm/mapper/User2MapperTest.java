package fm.mapper;

import fm.domain.User;
import fm.domain.UserSexEnum;
import fm.mapper.ds2.User2Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/10/17
 * Time: 1:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class User2MapperTest {
    @Resource
    private User2Mapper userMapper;

    @Test
    public void testInsert() {
        userMapper.insert(new User("aa", "a123456", UserSexEnum.MAN));
        userMapper.insert(new User("bb", "b123456", UserSexEnum.WOMAN));
        userMapper.insert(new User("cc", "b123456", UserSexEnum.WOMAN));
//        Assert.assertEquals(3, userMapper.getAll().size());
    }

    @Test
    public void testQuery() {
        List<User> users = userMapper.getAll();
        if (users == null || users.size() == 0) {
            System.out.println("is null");
        } else {
            System.out.println(users.toString());
        }
    }

    @Test
    public void testUpdate() {
        User user = userMapper.getOne(30L);
        System.out.println(user.toString());
        user.setNickName("neo");
        userMapper.update(user);
        Assert.assertEquals("neo", userMapper.getOne(30L).getNickName());
    }
}
