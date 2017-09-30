package upload.service;

import upload.entity.User;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/9/30
 * Time: 16:31
 */
public interface UserService {
    List<User> getUserList();

    User findUserById(long id);

    void save(User user);

    void edit(User user);

    void delete(long id);
}
