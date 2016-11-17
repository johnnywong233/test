package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 3:36
 */
public class ParserUtil {


    //refactor to a method
    public static String getContentFromUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s;
            StringBuilder sb = new StringBuilder("");
            while ((s = br.readLine()) != null) {
//                sb.append(s + "/r/n");
                sb.append(s);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            return "error open url:" + strUrl;
        }
    }
}
