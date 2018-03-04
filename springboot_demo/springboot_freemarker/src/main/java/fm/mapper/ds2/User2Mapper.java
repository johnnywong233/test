package fm.mapper.ds2;

import fm.domain.User;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/10/17
 * Time: 0:45
 */
public interface User2Mapper {
    List<User> getAll();

    User getOne(Long id);

    void insert(User user);

    void update(User user);

    void delete(Long id);
}
