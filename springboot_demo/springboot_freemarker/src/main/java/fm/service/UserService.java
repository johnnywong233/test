package fm.service;

import fm.domain.User;
import fm.domain.UserSexEnum;

/**
 * Created by Johnny on 2018/3/4.
 */
public interface UserService {
    /**
     * 新增一个用户
     */
    void create(Long id, String userName, String passWord, UserSexEnum userSex, String nickName);

    /**
     * 根据name删除一个用户高
     */
    void deleteByName(String name);

    /**
     * 获取用户总量
     */
    Integer getAllUsers();

    /**
     * 根据姓名查询
     */
    User getByName(String name);

    /**
     * 删除所有用户
     */
    void deleteAllUsers();
}