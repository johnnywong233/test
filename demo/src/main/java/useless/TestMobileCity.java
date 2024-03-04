package useless;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnny on 2016/8/16.
 * by use of taobao phone api to assert city
 */
public class TestMobileCity {

    static {
        trustSelfSignedSSL();//cannot fix "no name matching *** found" exception
//		try {
////			fixUntrustCertificate();
//		} catch (KeyManagementException|NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
    }

    /**
     * by use of taobao's API to cal input phone number come from which city
     *
     * @param mobileNumber test phone number
     */
    private static String calcMobileCity(String mobileNumber) throws MalformedURLException {
        String jsonString;
        JSONArray array;
        JSONObject jsonObject = null;
        //在公司运行时(并没有设置代理，公司内网本身就可以访问外网，使用代理只是为了让访问速度更快一些，稳定性更好),
        //use https use throw java.security.cert.CertificateException: No name matching tcc.taobao.com found
        //while use http, will throw a Hewlett Packard Enterprise Wrong Autocache
        //意思是需要在代码层面设置代理，一定要注意在执行API的request请求之前添加下面的代码;并且在设置代理之后，报错也变成301
        //可是在添加代理之后，换成https还是有No name matching tcc.taobao.com found异常信息

        //but at home, use https will get the right result, while use http throw net.sf.json.JSONException:301 Moved Permanently, 
        //The requested resource has been assigned a new permanent URI.Powered by Tengine/Aserver
        String urlString = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + mobileNumber;
        StringBuilder sb = new StringBuilder();
        BufferedReader buffer;
        URL url = new URL(urlString);
        try {
            InputStream in = url.openStream();
            //handle messy code problem
            buffer = new BufferedReader(new InputStreamReader(in, "gb2312"));
            String line;
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            buffer.close();
            jsonString = sb.toString();
            //replace “__GetZoneResult_ = ” in order to convert into JSONArray
            jsonString = jsonString.replaceAll("^[__]\\w{14}+[_ = ]+", "[");
            String jsonString2 = jsonString + "]";
            System.out.println(jsonString2);
            // convert string to json array
            array = JSONArray.fromObject(jsonString2);
            // 获取JSONArray的JSONObject对象，便于读取array里的键值对
            jsonObject = array.getJSONObject(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            return jsonObject.getString("province");
        }
        return null;
    }

    /**
     * get the cities of several numbers
     *
     * @param mobileNumbers number list
     */
    private static JSONObject calcMobilesCities(List<String> mobileNumbers) throws MalformedURLException {
        JSONObject jsonNumberCity = new JSONObject();
        for (String mobileNumber : mobileNumbers) {
            jsonNumberCity.put(mobileNumber, calcMobileCity(mobileNumber));
        }
        return jsonNumberCity;
    }

    @SuppressWarnings("unused")
    private static String calcMobileCity1(String mobileNumber) throws IOException {
        String url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + mobileNumber;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        // Getting the response body.
        HttpGet httpGet = new HttpGet(url);
        ResponseHandler<String> handler = new BasicResponseHandler();
        return client.execute(httpGet, handler);
    }

    /**
     * java去除https证书验证,java进行https协议网络请求时，会要求证书验证。
     */
    private static void trustSelfSignedSSL() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @SuppressWarnings("unused")
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) {
                }

                @SuppressWarnings("unused")
                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] arg0, String arg1) {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] arg0, String arg1) {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLContext.setDefault(ctx);
        } catch (Exception ex) {
            throw new RuntimeException("Exception occurred ", ex);
        }
    }

    public static void fixUntrustCertificate() throws KeyManagementException, NoSuchAlgorithmException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {

                    }

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {

                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HostnameVerifier allHostsValid = (hostname, session) -> true;

        // set the  allTrusting verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }


    //http://www.jb51.net/article/43774.htm
    public static void main(String[] args) throws Exception {

        DisableSSLCertificateCheckUtil.disableChecks();
        //set proxy
        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", "web-proxy.sgp.hpecorp.net");//web-proxy.sgp.hpecorp.net:8088, web-proxy.sgp.hp.com
        System.setProperty("http.proxyPort", "8088");//8080

        // Unexpected end of file from server


        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//        HttpsURLConnection.setDefaultHostnameVerifier ((hostname, session) -> true);

        //if null number, then return
        String testMobileNumber = "15216772095";
        System.out.println(calcMobileCity(testMobileNumber));
        List<String> mobileList = new ArrayList<>();
        for (int i = 1350345; i < 1350346; i++) {
            mobileList.add(String.valueOf(i));
        }
        System.out.println(calcMobilesCities(mobileList));
    }
}
