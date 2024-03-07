package project.httpclient;

import project.httpclient.dto.UserDTO;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:56
 */
public interface HelloService {
    String processService(String name);

    UserDTO processService(UserDTO userDTO);
}
