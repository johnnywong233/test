package mybatis.service.impl;

import jakarta.annotation.Resource;
import mybatis.model.User;
import mybatis.model.mapper.UserMapper;
import mybatis.service.UserService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserServerImpl implements UserService {
    @Resource
    private UserMapper mapper;

    @Override
    public boolean add(User user) {
        return mapper.insert(user) > 0;
    }

    @Override
    public List<User> findAll() {
        return mapper.selectByExample(null);
    }

}
