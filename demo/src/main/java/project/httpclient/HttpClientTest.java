package project.httpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:32
 */
public class HttpClientTest {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    public static void main(String[] args) {
        getRequestTest();
        getRequestTest2();
        postRequestTest();
    }

    private static void getRequestTest() {
        String url = "http://localhost:8080/SpringMVC/greeting?name=lisi";
        try {
            String str = HttpClientUtil.doGet(url, "UTF-8");
            if (str != null) {
                logger.info("http Get request result:" + str);
            } else {
                logger.info("http Get request process fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getRequestTest2() {

        String url = "http://localhost:8080/SpringMVC/greeting?name=lisi";
        try {
            String str = HttpClientUtil.doGet2(url, "UTF-8");
            if (str != null) {
                logger.info("http Get request result:" + str);
            } else {
                logger.info("http Get request process fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void postRequestTest() {
        String url = "http://localhost:8080/SpringMVC/processing";
        Map<String, Object> _params = new HashMap<>();
        _params.put("name", "johnny");
        _params.put("age", 25);
        String str = HttpClientUtil.doPost(url, _params, "UTF-8", true);
        if (str != null) {
            logger.info("http Post request result:" + str);
        } else {
            logger.info("http Post request process fail");
        }
    }
}
