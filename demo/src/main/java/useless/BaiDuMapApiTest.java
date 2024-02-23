package useless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/11/16
 * Time: 22:03
 */
public class BaiDuMapApiTest {

    public static void main(String[] args) {
        BaiDuMapApiTest test = new BaiDuMapApiTest();
        System.out.println(test.getLatAndLngByAddress("https://api.map.baidu.com/place/v2/search/shanghai"));
    }

    /*
     * baidu map api: http://developer.baidu.com/map/
     * http://developer.baidu.com/map/index.php?title=webapi/guide/webservice-geocoding
     * http://blog.csdn.net/u013142781/article/details/47085369
     * 在家里，如果设置web-proxy.atl.hp.com:8080代理的话，会报异常信息:java.net.ConnectException: Connection timed out: connect
     * 取消代理即可正常使用API。
     * 公司的笔记本，无论怎么设置，都会报异常信息：java.lang.StringIndexOutOfBoundsException: String index out of range: -6
     */
    public Map<String, BigDecimal> getLatAndLngByAddress(String add) {
        String lat = "";
        String lng = "";
        String address = java.net.URLEncoder.encode(add, StandardCharsets.UTF_8);
        URL myURL = null;
        URLConnection httpsConn = null;
        //进行转码
        try {
            String url = String.format("https://api.map.baidu.com/geocoder/v2/?ak=4rcKAZKG9OIl0wDkICSLx8BA&output=json&address=%s", address);
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            if (myURL != null) {
                httpsConn = myURL.openConnection();
            }
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(insr);
                String data;
                if ((data = br.readLine()) != null) {
                    lat = data.substring(data.indexOf("\"lat\":") + ("\"lat\":").length(), data.indexOf("},\"precise\""));
                    lng = data.substring(data.indexOf("\"lng\":") + ("\"lng\":").length(), data.indexOf(",\"lat\""));
                }
                insr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("lat", new BigDecimal(lat));
        map.put("lng", new BigDecimal(lng));
        return map;
    }
}