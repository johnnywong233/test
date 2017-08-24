package project.httpclient;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.httpclient.dto.UserDTO;
import utils.HttpClientUtil;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:34
 */
public class HttpRequestTest {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    public static void main(String[] args) throws Exception {
        getRequestTest();
        postRequestTest();
    }

    private static void getRequestTest() {
        String url = "http://localhost:8080/SpringMVC/greet?name=lisi";
        JSONObject jsonObject = HttpClientUtil.httpGet(url);
        if (jsonObject != null) {
            String userName = (String) jsonObject.get("userName");
            logger.info("http Get request process success");
            logger.info("userName:" + userName);
        } else {
            logger.info("http Get request process fail");
        }
    }

    private static void postRequestTest() throws Exception {
        String url = "http://localhost:8080/SpringMVC/process";
        UserDTO userDTO = new UserDTO();
        userDTO.setName("johnny");
        userDTO.setAge(25);
        JSONObject jsonParam = JSONObject.fromObject(userDTO);
        JSONObject responseJSONObject = HttpClientUtil.httpPost(url, jsonParam);
        if (responseJSONObject != null && "SUCCESS".equals(responseJSONObject.get("status"))) {
            JSONObject userStr = (JSONObject) responseJSONObject.get("userDTO");
            userDTO = (UserDTO) JSONObject.toBean(userStr, UserDTO.class);
            logger.info("http Post request process success");
            logger.info("userDTO:" + userDTO);
        } else {
            logger.info("http Post request process fail");
        }
    }
}
