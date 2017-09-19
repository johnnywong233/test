package mybatis.service;

import mybatis.model.User;

import java.util.List;

public interface UserService {
    boolean add(User user);

    List<User> findAll();
}
