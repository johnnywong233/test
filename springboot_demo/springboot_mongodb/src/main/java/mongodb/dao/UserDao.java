package mongodb.dao;

import mongodb.bean.UserEntity;

/**
 * Author: Johnny
 * Date: 2017/9/15
 * Time: 18:45
 */
public interface UserDao {
    void saveUser(UserEntity user);
    UserEntity findUserByUserName(String userName);
    void deleteUserById(Long id);
    void updateUser(UserEntity user);
}
