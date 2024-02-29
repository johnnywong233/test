package mongodb;

import mongodb.bean.UserEntity;
import mongodb.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 18:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Resource
    private UserDao userDao;

    @Test
    public void testSaveUser() {
        UserEntity user = new UserEntity();
        user.setId(2L);
        user.setUserName("johnny");
        user.setPassWord("master");
        userDao.saveUser(user);
    }

    @Test
    public void findUserByUserName() {
        UserEntity user = userDao.findUserByUserName("johnny");
        System.out.println("user is " + user);
    }

    @Test
    public void updateUser() {
        UserEntity user = new UserEntity();
        user.setId(2L);
        user.setUserName("sky");
        user.setPassWord("rocket");
        userDao.updateUser(user);
    }

    @Test
    public void deleteUserById() {
        userDao.deleteUserById(1L);
    }
}
