package shrio.core.repository;

import shrio.core.bean.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    /**
     * 通过username查找用户信息;
     */
    UserInfo findByUsername(String username);

}
