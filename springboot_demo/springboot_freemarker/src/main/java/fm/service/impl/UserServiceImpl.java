package fm.service.impl;

import fm.domain.User;
import fm.domain.UserSexEnum;
import fm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Created by Johnny on 2018/3/4.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(Long id, String userName, String passWord, UserSexEnum userSex, String nickName) {
        jdbcTemplate.update("insert into users(id, userName, passWord, user_sex, nick_name) values(?, ?, ?, ?, ?)", id, userName, passWord, userSex, nickName);
    }

    @Override
    public void deleteByName(String username) {
        jdbcTemplate.update("delete from users where userName = ?", username);
    }

    @Override
    public User getByName(String username) {
        //TODO：org.springframework.jdbc.IncorrectResultSetColumnCountException: Incorrect column count: expected 1, actual 5
//        return jdbcTemplate.queryForObject("select * from users where userName = ?", User.class, username);

//        RowMapper<User> rm = ParameterizedBeanPropertyRowMapper.newInstance(User.class);
        //不行
        RowMapper<User> rm = BeanPropertyRowMapper.newInstance(User.class);
        //org.springframework.dao.EmptyResultDataAccessException: Incorrect result size: expected 1, actual 0
        return jdbcTemplate.queryForObject("select * from users where userName = ? ORDER BY id DESC limit 0, 1 ", rm, User.class);

        //不行，无论是 limit，还是后面的 get(0)，都失败：Incorrect column count: expected 1, actual 5
//        return jdbcTemplate.queryForList("select * from users where userName = ? limit 0, 1", User.class, username).get(0);
    }

    @Override
    public Integer getAllUsers() {
        return jdbcTemplate.queryForObject("select count(1) from users", Integer.class);
    }

    @Override
    public void deleteAllUsers() {
        jdbcTemplate.update("delete from users");
    }
}