package demo1.server;

import java.util.List;

import demo1.model.User;

public interface UserServer {
    /**
     * add one user
     */
    public boolean add(User user);

    /**
     * query all users
     */
    public List<User> findAll();
}
