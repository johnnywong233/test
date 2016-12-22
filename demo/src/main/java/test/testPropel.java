package test;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

//import net.sf.json.JSONObject;//千万不要随意用jar包

public class testPropel {
    /*
     * 编写java程序实现rest client的类似功能
     * 首先POST 得到X-Auth-Token
     */
    public static void main(String[] args) throws IOException,
            KeyManagementException, NoSuchAlgorithmException, JSONException {
        trustAllHosts();

        String urlPOST = "https://propel4itba.ftc.hpeswlab.net:9600/idm-service/v2.0/tokens";

        final String userPassword = "idmTransportUser" + ":" + "idmTransportUser";
        final String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());

        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

        //创建连接
        HttpURLConnection connection = (HttpURLConnection) new URL(urlPOST).openConnection();

        //设置http连接属性
        connection.setRequestMethod("POST");

        //设置http头 消息
        connection.setRequestProperty("Authorization", "Basic" + encoding);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        //POST时需要 添加 请求内容
        JSONObject user = new JSONObject();
        user.put("username", "orgadmin");
        user.put("password", "propel");//仔细啊！！

        //构建嵌套的 json数据
        JSONObject obj = new JSONObject();
        obj.put("passwordCredentials", user);
        obj.put("tenantName", "CONSUMER");

//		OutputStream out = connection.getOutputStream();
//		out.write(obj.toString().getBytes());  
//		out.flush();  
//		out.close(); 


        System.out.println("Basic " + encoding);
        System.out.println("" + connection.getResponseCode());

        //得到 输出的JSON格式的response
        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        System.out.println(sb.toString());


        //从JSON格式的response中解析得到X-Auth-Token

        String urlGET = "https://propel4itba.ftc.hpeswlab.net:9540/approval";  //approval可以 200 /bower_components不行 404
//		String Token = "eyJ0eXAiOiJKV1MiLCJhbGciOiJIUzI1NiJ9.eyJjb20uaHAuY2xvdWQ6cm9sZTpDT05TVU1FUiI6dHJ1ZSwiY29tLmhwLmNsb3VkOnJvbGU6U1VQUExJRVJfQURNSU4iOnRydWUsImNvbS5ocC5jbG91ZDp0ZW5hbnQiOnsiaWQiOiJmZjgwODA4MTQ4MTJlMjJiMDE0ODEzMDBhYjBkMDAwMiIsIm5hbWUiOiJDT05TVU1FUiIsImRlc2NyaXB0aW9uIjoiQ29uc3VtZXIiLCJlbmFibGVkIjp0cnVlfSwicHJuIjoib3JnYWRtaW4iLCJjb20uaHAuY2xvdWQ6cm9sZTpBR0dSRUdBVElPTl9BRE1JTiI6dHJ1ZSwiY29tLmhwLmNsb3VkOnJvbGU6Q0FUQUxPR19BRE1JTiI6dHJ1ZSwicmVmcmVzaF90b2tlbiI6ImZvS1h0ZlI0M1U2WS9QeWtaTWtlSm9aZEdNaHVjQTFpcHNRYS9pbnhXdUg5cXVFbFUvYTEzajN2NHVlUVFZZjE3Qm1NcVZqRUJwYU1BQ0hRTFdkbE5PVGlYWkN2ZDdCelJmTG1IYW9MN25RNTFiQzJ1dk9uYVFpVFluL1IvN2FkcTlpZEVXTXRFSnVIOW9GbERrczVjUVlDdGFxWXl1UDZYNlM4QWE4RUtPcjEzTTU4aVJCZGJzUS9DMXNxeVdUWGFiN09rL2ZoNWFXeHBEQ3l0ZklMZ01rRHdJMzM3ODAwZ0x3dm5xWUtIalA2K1N6VDIyNjZyYUVjSVJaTDhUVXJZbWJUYjhsY3NCYnhpUTg4Z2tML29qbUkydDdURE4vb3g2STFkRFFmTUpmSXZLV1lkbmxuQ0hQZjVUTnIwd1Z6ZUYzL1FOemphWVRBV1lBT2NxNEVpUk1Dblo3TUgxSWpwMVpadllrRllZKzZvTFV0dlN6dkh5VXZHVXpvUmFQTjFWMjhyZlpsZ2VOM1VoT2ZNQWU4ZCsyc3NWemhKOStyMHJLRDNqQUFBRVJnbnBPcmJnc2dBM3NzQUlpZndhQ0dVNU5saUhWY3FIbk52SVp2SnlDNHNVN3hsMTBRRERmYkVPUmhtT3plek5IUWM4WUVWemJndVhTUXEwZlFaZTY2VmtzZ2t3Q011M0p3UUlwWHhZTDY4ZzRnMC8wS3puSW9BckViRXZsNzBLZHQ5ek1ZdWlZQkJzWHJzOTA2SEFoSmlMQXhBb05odU9wZUtSZ0doQklIMWdiM0VhRGluTC91MHZaVU8zeG1XNmtyMW5WTUxsRUgvUFZ3c2E5ZGJEQW5qUzdKWnZ1NkdSZkpaaGVuVVRVZ1d5VVl2eU9DV2NZa3E5MEhReGkzMURKVHVnQm1qd3A2bVZRM3BWZ2NYWXduSkZVUHNucyswNVk9IiwiY29tLmhwLmNsb3VkOnJvbGU6Uk9MRV9SRVNUIjp0cnVlLCJjb20uaHAuY2xvdWQ6cm9sZTpTVVBQT1JUIjp0cnVlLCJjb20uaHAuY2xvdWQ6cm9sZTpTVUJTQ1JJUFRJT05fQURNSU4iOnRydWUsImV4cCI6MTQ1ODU1NzQ0NywiaWF0IjoxNDU4NTU1NjQ3LCJjb20uaHAuY2xvdWQ6cm9sZTpJRE1fQURNSU4iOnRydWV9.W3HoljtXGgeHPiZjIFjJbSVQtKtSe_ZPFlQM0OtWPic";

        //创建连接
        HttpURLConnection conn = (HttpURLConnection) new URL(urlGET).openConnection();

        //设置http连接属性
        conn.setRequestMethod("GET");

        //设置http头 消息
//		conn.setRequestProperty("Authorization", "Basic" + encoding);
        conn.setRequestProperty("Accept", "application/json");
//		conn.setRequestProperty("X-Auth-Token",Token);

        conn.connect();
        System.out.println("" + conn.getResponseCode());


        //得到 输出的JSON格式的response
        BufferedReader br1 = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder sb1 = new StringBuilder();
        String output1;
        while ((output1 = br1.readLine()) != null) {
            sb1.append(output1);
        }
        System.out.println(sb1.toString());

    }

    private static void trustAllHosts() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }


}
