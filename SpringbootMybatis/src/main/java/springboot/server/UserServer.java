package springboot.server;

import java.util.List;

import springboot.model.User;

public interface UserServer {
    boolean add(User user);

    List<User> findAll();
}
