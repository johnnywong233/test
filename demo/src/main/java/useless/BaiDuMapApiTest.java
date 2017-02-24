package useless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
        String addre = "http://api.map.baidu.com/place/v2/search/shanghai";
        System.out.println(test.getLatAndLngByAddress(addre));
    }

    /*
     * baidu map api: http://developer.baidu.com/map/
     * http://developer.baidu.com/map/index.php?title=webapi/guide/webservice-geocoding
     * http://blog.csdn.net/u013142781/article/details/47085369
     * 在家里，如果设置web-proxy.atl.hp.com:8080代理的话，会报异常信息:java.net.ConnectException: Connection timed out: connect
     * 取消代理即可正常使用API。
     * 公司的笔记本，无论怎么设置，都会报异常信息：java.lang.StringIndexOutOfBoundsException: String index out of range: -6
     */
    public Map<String, BigDecimal> getLatAndLngByAddress(String addr) {
        String address = "";
        String lat = "";
        String lng = "";
        try {
            address = java.net.URLEncoder.encode(addr, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String url = String.format("http://api.map.baidu.com/geocoder/v2/?"
                + "ak=4rcKAZKG9OIl0wDkICSLx8BA&output=json&address=%s", address);
        URL myURL = null;
        URLConnection httpsConn = null;
        //进行转码
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            if (myURL != null) {
                httpsConn = myURL.openConnection();
            }
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data;
                if ((data = br.readLine()) != null) {
                    lat = data.substring(data.indexOf("\"lat\":")
                            + ("\"lat\":").length(), data.indexOf("},\"precise\""));
                    lng = data.substring(data.indexOf("\"lng\":")
                            + ("\"lng\":").length(), data.indexOf(",\"lat\""));
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