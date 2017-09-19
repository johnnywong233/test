package mybatis.service.impl;

import mybatis.model.User;
import mybatis.model.mapper.UserMapper;
import mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserServerImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    public boolean add(User user) {
        return mapper.insert(user) > 0;
    }

    public List<User> findAll() {
        return mapper.selectByExample(null);
    }

}
