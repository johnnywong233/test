package useless;

import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wajian on 2016/8/16.
 * //http://www.jb51.net/article/47998.htm
 */
public class GetAddressByIp {
    //get the belonging place(country, area, city and isp) with the given ip
    public static void main(String args[]) {
        System.out.println(GetAddressByIp.getAddressByIp("60.214.183.158"));//60.214.183.158 //120.192.182.1
    }

    private static String getAddressByIp(String ip) {
        String result;
        try {
            //https not working!
            String str = getJsonContent("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
            System.out.println(str);

            JSONObject obj = JSONObject.fromObject(str);
            JSONObject obj2 = (JSONObject) obj.get("data");
            int code = (int) obj.get("code");
            if (code == 0) {
                result = obj2.get("country") + "--" + obj2.get("area") + "--" + obj2.get("city") + "--" + obj2.get("isp");
            } else {
                result = "wrong IP address";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "get IP address exception:" + e.getMessage();
        }
        return result;
    }

    private static String getJsonContent(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            //set the connection properties
            httpConn.setConnectTimeout(3000);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            //get the response code
            int respCode = httpConn.getResponseCode();
            if (respCode == 200) {
                return convertStream2Json(httpConn.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String convertStream2Json(InputStream inputStream) {
        String jsonStr = "";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            jsonStr = new String(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
