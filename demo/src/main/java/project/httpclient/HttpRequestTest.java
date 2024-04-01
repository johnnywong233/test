package project.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import project.httpclient.dto.UserDTO;
import utils.HttpClientUtil;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:34
 */
@Slf4j
public class HttpRequestTest {

    public static void main(String[] args) throws Exception {
        getRequestTest();
        postRequestTest();
    }

    private static void getRequestTest() {
        String url = "http://localhost:8080/SpringMVC/greet?name=lisi";
        JSONObject jsonObject = HttpClientUtil.httpGet(url);
        if (jsonObject != null) {
            String userName = (String) jsonObject.get("userName");
            log.info("http Get request process success");
            log.info("userName:" + userName);
        } else {
            log.info("http Get request process fail");
        }
    }

    private static void postRequestTest() {
        String url = "http://localhost:8080/SpringMVC/process";
        UserDTO userDTO = new UserDTO();
        userDTO.setName("johnny");
        userDTO.setAge(25);
        JSONObject jsonParam = new JSONObject(userDTO);
        JSONObject responseJSONObject = HttpClientUtil.httpPost(url, jsonParam);
        if (responseJSONObject != null && "SUCCESS".equals(responseJSONObject.get("status"))) {
            JSONObject userStr = (JSONObject) responseJSONObject.get("userDTO");
            userDTO = (UserDTO) JSONObject.wrap(userStr);
            log.info("http Post request process success");
            log.info("userDTO:" + userDTO);
        } else {
            log.info("http Post request process fail");
        }
    }
}
