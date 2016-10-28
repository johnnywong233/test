package project.httpclient;

import project.httpclient.dto.UserDTO;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:56
 */
public interface HelloService {
    public String processService(String name) throws Exception;

    public UserDTO processService(UserDTO userDTO);
}
