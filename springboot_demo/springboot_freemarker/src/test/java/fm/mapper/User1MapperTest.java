package fm.mapper;

import fm.domain.User;
import fm.domain.UserSexEnum;
import fm.mapper.ds1.User1Mapper;
import fm.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class User1MapperTest {
    @Resource
    private User1Mapper userMapper;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        // 准备，清空user表
        userService.deleteAllUsers();
    }

    @Test
    public void testInsert() {
        userMapper.insert(new User("Daniel", "root", UserSexEnum.MAN));
        userMapper.insert(new User("Craig", "1Qaz2wsx", UserSexEnum.WOMAN));
        userMapper.insert(new User("Johnny", "iso*help", UserSexEnum.WOMAN));
//        Assert.assertEquals(6, userMapper.getAll().size());
    }

    @Test
    public void testQuery() {
        List<User> users = userMapper.getAll();
        if (users == null || users.size() == 0) {
            System.out.println("is null");
        } else {
            System.out.println(users.size());
        }
    }

    @Test
    public void testUpdate() {
        userMapper.insert(new User("Johnny", "root", UserSexEnum.MAN));
        User user = userService.getByName("Johnny");
        System.out.println(user.toString());
        user.setNickName("johnny");
        userMapper.update(user);
        Assert.assertEquals("johnny", userMapper.getOne(50L).getNickName());
    }
}