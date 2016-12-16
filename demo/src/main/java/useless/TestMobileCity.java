package useless;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wajian on 2016/8/16.
 * by use of taobao phone api to assert city
 */
public class TestMobileCity {
    /**
     * 测试手机号码是来自哪个城市的，利用淘宝的API
     * @param mobileNumber test phone number
     * @return
     * @throws MalformedURLException
     */
    public static String calcMobileCity(String mobileNumber) throws MalformedURLException{
        String jsonString;
        JSONArray array;
        JSONObject jsonObject = null;
        String urlString = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + mobileNumber;
        StringBuilder sb = new StringBuilder();
        BufferedReader buffer;
        URL url = new URL(urlString);
        try{
            InputStream in = url.openStream();
            //handle messy code problem
            buffer = new BufferedReader(new InputStreamReader(in, "gb2312"));
            String line;
            while((line = buffer.readLine()) != null){
                sb.append(line);
            }
            in.close();
            buffer.close();
            jsonString = sb.toString();
            // 替换掉“__GetZoneResult_ = ”，让它能转换为JSONArray对象
            jsonString = jsonString.replaceAll("^[__]\\w{14}+[_ = ]+", "[");
            // System.out.println(jsonString+"]");
            String jsonString2 = jsonString + "]";
            //TODO
            // convert string to json array
//            array = JSONArray.fromObject(jsonString2);
            array = JSONArray.fromObject(Class.forName(jsonString2).newInstance());

            // 获取JSONArray的JSONObject对象，便于读取array里的键值对
            jsonObject = array.getJSONObject(0);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (jsonObject != null) {
            return jsonObject.getString("province");
        }
        return null;
    }

    /**
     * get the cities of several numbers
     * @param mobileNumbers number list
     * @return
     * @throws MalformedURLException
     */
    public static JSONObject calcMobilesCities(List<String> mobileNumbers) throws MalformedURLException {
        JSONObject jsonNumberCity = new JSONObject();
        for(String mobileNumber : mobileNumbers){
            jsonNumberCity.put(mobileNumber, calcMobileCity(mobileNumber));
        }
        return jsonNumberCity;
    }

    //http://www.jb51.net/article/43774.htm
    public static void main(String[] args) throws Exception{
    	//if null number, then return 
        String testMobileNumber = "15216772095"; //1881758452
        System.out.println(calcMobileCity(testMobileNumber));
        List<String> mobileList = new ArrayList<>();
        for(int i = 1350345; i < 1350388; i++){
            mobileList.add(String.valueOf(i));
        }
        System.out.println(calcMobilesCities(mobileList).toString());
    }
}
