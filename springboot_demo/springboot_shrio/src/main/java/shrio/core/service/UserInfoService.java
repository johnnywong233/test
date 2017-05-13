package shrio.core.service;

import shrio.core.bean.UserInfo;

public interface UserInfoService {

    /**
     * 通过username查找用户信息;
     */
    UserInfo findByUsername(String username);

}
