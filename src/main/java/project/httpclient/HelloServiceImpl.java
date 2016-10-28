package project.httpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import project.httpclient.dto.UserDTO;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:57
 */
@Service("helloService")
public class HelloServiceImpl implements HelloService {

    private static Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String processService(String name) throws Exception {
        logger.info("HelloService processService name:" + name);
        return "Hello " + name;
    }

    @Override
    public UserDTO processService(UserDTO userDTO) {
        logger.info("HelloService processService userDTO:" + userDTO);
        userDTO.setName("Hi " + userDTO.getName());
        userDTO.setAge(1 + userDTO.getAge());
        return userDTO;
    }
}
