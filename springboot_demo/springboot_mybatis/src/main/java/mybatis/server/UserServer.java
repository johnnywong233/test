package mybatis.server;

import java.util.List;

import mybatis.model.User;

public interface UserServer {
    boolean add(User user);

    List<User> findAll();
}
