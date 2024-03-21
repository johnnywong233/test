package project.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import project.httpclient.dto.UserDTO;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:57
 */
@Slf4j
@Service("helloService")
public class HelloServiceImpl implements HelloService {
    @Override
    public String processService(String name) {
        log.info("HelloService processService name:" + name);
        return "Hello " + name;
    }

    @Override
    public UserDTO processService(UserDTO userDTO) {
        log.info("HelloService processService userDTO:" + userDTO);
        userDTO.setName("Hi " + userDTO.getName());
        userDTO.setAge(1 + userDTO.getAge());
        return userDTO;
    }
}
