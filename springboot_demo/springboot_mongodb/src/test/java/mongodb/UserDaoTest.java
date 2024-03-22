package mongodb;

import jakarta.annotation.Resource;
import mongodb.bean.UserEntity;
import mongodb.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 18:44
 */
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
