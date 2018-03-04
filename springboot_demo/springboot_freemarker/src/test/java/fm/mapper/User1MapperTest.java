package fm.mapper;

import fm.domain.User;
import fm.domain.UserSexEnum;
import fm.mapper.ds1.User1Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/10/17
 * Time: 1:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class User1MapperTest {
    @Autowired
    private User1Mapper userMapper;

    @Test
    public void testInsert() throws Exception {
        userMapper.insert(new User("Daniel", "root", UserSexEnum.MAN));
        userMapper.insert(new User("Craig", "1Qaz2wsx", UserSexEnum.WOMAN));
        userMapper.insert(new User("Johnny", "iso*help", UserSexEnum.WOMAN));
//        Assert.assertEquals(6, userMapper.getAll().size());
    }

    @Test
    public void testQuery() throws Exception {
        List<User> users = userMapper.getAll();
        if (users == null || users.size() == 0) {
            System.out.println("is null");
        } else {
            System.out.println(users.size());
        }
    }

    @Test
    public void testUpdate() throws Exception {
        userMapper.insert(new User("Johnny", "root", UserSexEnum.MAN));
        User user = userMapper.getOne(50L);
        System.out.println(user.toString());
        user.setNickName("johnny");
        userMapper.update(user);
        Assert.assertTrue(("johnny".equals(userMapper.getOne(50L).getNickName())));
    }
}