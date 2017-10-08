package mongodb.service;

import mongodb.model.User;

/**
 * Author: Johnny
 * Date: 2017/10/8
 * Time: 17:03
 */
public interface AuthService {
    User register(User userToAdd);

    String login(String username, String password);

    String refresh(String oldToken);
}
