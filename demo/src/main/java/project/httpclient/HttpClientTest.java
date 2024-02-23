package project.httpclient;

import lombok.extern.slf4j.Slf4j;
import utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:32
 */
@Slf4j
public class HttpClientTest {

    public static void main(String[] args) {
        getRequestTest();
        getRequestTest2();
        postRequestTest();
    }

    private static void getRequestTest() {
        String url = "http://localhost:8080/SpringMVC/greeting?name=lisi";
        try {
            String str = HttpClientUtil.doGet(url, "UTF-8");
            log.info("http Get request result:" + str);
        } catch (Exception e) {
            log.error("getRequestTest fail", e);
        }
    }

    private static void getRequestTest2() {
        String url = "http://localhost:8080/SpringMVC/greeting?name=lisi";
        try {
            String str = HttpClientUtil.doGet2(url, "UTF-8");
            if (str != null) {
                log.info("http Get request result:" + str);
            } else {
                log.info("http Get request process fail");
            }
        } catch (Exception e) {
            log.error("getRequestTest fail", e);
        }
    }

    private static void postRequestTest() {
        String url = "http://localhost:8080/SpringMVC/processing";
        Map<String, Object> params = new HashMap<>();
        params.put("name", "johnny");
        params.put("age", 25);
        String str = HttpClientUtil.doPost(url, params, "UTF-8", true);
        log.info("http Post request result:" + str);
    }
}
