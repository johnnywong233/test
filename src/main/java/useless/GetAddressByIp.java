package useless;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wajian on 2016/8/16.
 */
public class GetAddressByIp {
	//http://www.jb51.net/article/47998.htm
	//java联网查询IP归属地，原理是根据淘宝提供的service查询IP的归属地并且解析http请求返回的json串
    public static void main(String args[]){
        System.out.println(GetAddressByIp.GetAddressByIp("60.214.183.158"));//60.214.183.158 //120.192.182.1
    }

    //
    public static String GetAddressByIp(String IP){
        String resout = "";
        try{
        	//https not working?
            String str = getJsonContent("http://ip.taobao.com/service/getIpInfo.php?ip=" + IP);
            System.out.println(str);

            //TODO
            JSONObject obj = JSONObject.fromObject(str);//the original
//            org.json.JSONObject obj = new org.json.JSONObject(str);//not ok
//            JSONObject obj = JSONObject.fromObject(Class.forName(str).newInstance());
//            JSONObject obj = JSONObject.fromObject(str.getClass());
            
            JSONObject obj2 = (JSONObject) obj.get("data");
            String code = (String) obj.get("code");
            if(code.equals("0")){
                resout = obj2.get("country") + "--" + obj2.get("area") + "--" + obj2.get("city") + "--" + obj2.get("isp");
            }else{
                resout = "wrong IP address";
            }
        }catch(Exception e){
            e.printStackTrace();
            resout = "get IP address exception:" + e.getMessage();
        }
        return resout;
    }

    public static String getJsonContent(String urlStr) {
        try {
        	// 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            //set the connection properties
            httpConn.setConnectTimeout(3000);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            //get the response code
            int respCode = httpConn.getResponseCode();
            if (respCode == 200) {
                return ConvertStream2Json(httpConn.getInputStream());
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    private static String ConvertStream2Json(InputStream inputStream) {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
