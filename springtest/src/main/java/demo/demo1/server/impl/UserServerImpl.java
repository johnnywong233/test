package demo1.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo1.model.User;
import demo1.model.mapper.UserMapper;
import demo1.server.UserServer;

/**
 * 用户相关数据库操作实现类
 */
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
