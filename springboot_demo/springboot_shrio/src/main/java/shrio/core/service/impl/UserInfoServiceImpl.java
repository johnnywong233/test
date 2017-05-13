package shrio.core.service.impl;

import shrio.core.bean.UserInfo;
import shrio.core.repository.UserInfoRepository;
import shrio.core.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoRepository.findByUsername(username);
    }

}