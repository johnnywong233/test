package springboot.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springboot.model.User;
import springboot.model.mapper.UserMapper;
import springboot.server.UserServer;

@Repository
public class UserServerImpl implements UserServer {
    @Autowired
    private UserMapper mapper;

    public boolean add(User user) {
        return mapper.insert(user) > 0;
    }

    public List<User> findAll() {
        return mapper.selectByExample(null);
    }

}
