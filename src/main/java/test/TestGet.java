package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONException;
import org.json.JSONObject;

public class TestGet {
//	public static final String ADD_URL = "https://propel4itba.ftc.hpeswlab.net:9600/idm-service/v2.0/tokens";  
	public static final String ADD_URL = "https://propel4itba.ftc.hpeswlab.net:9540/approval";
	
    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException, JSONException {
    	trustAllHosts();
        appadd();  
    }
	    public static void appadd() throws IOException, JSONException {
	        try {  
	        	//Server redirected too many  times (20)的解决方法
	        	CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

	             //创建连接 
	             HttpURLConnection connection = (HttpURLConnection) new URL(ADD_URL).openConnection();
	  
	             //设置http连接属性
	             connection.setDoOutput(true);  
	             connection.setDoInput(true);  
	             connection.setRequestMethod("POST"); // 可以根据需要 提交 GET、POST、DELETE、INPUT等http提供的功能  
	             connection.setUseCaches(false);  
	             connection.setInstanceFollowRedirects(true);  
	               
	             
	             final String X_Auth_Token = "eyJ0eXAiOiJKV1MiLCJhbGciOiJIUzI1NiJ9.eyJjb20uaHAuY2xvdWQ6cm9sZTpDT05TVU1FUiI6dHJ1ZSwiY29tLmhwLmNsb3VkOnJvbGU6U1VQUExJRVJfQURNSU4iOnRydWUsImNvbS5ocC5jbG91ZDp0ZW5hbnQiOnsiaWQiOiJmZjgwODA4MTQ4MTJlMjJiMDE0ODEzMDBhYjBkMDAwMiIsIm5hbWUiOiJDT05TVU1FUiIsImRlc2NyaXB0aW9uIjoiQ29uc3VtZXIiLCJlbmFibGVkIjp0cnVlfSwicHJuIjoib3JnYWRtaW4iLCJjb20uaHAuY2xvdWQ6cm9sZTpBR0dSRUdBVElPTl9BRE1JTiI6dHJ1ZSwiY29tLmhwLmNsb3VkOnJvbGU6Q0FUQUxPR19BRE1JTiI6dHJ1ZSwicmVmcmVzaF90b2tlbiI6IlY3Y3RQTUxnWnMza0NyRGRPellpd3RyYXIrV3pOMFVsSGFCSDNCaGE3TUl0aWJmVkREa2s0NnZRTUVHdUduMWZ4elNpcjJHdENPUW9nN2xIVEtxdEFjMzdVZGp6Z243NVFMMHVLNS9ZZXB5d3V3ZXdWUkpQZG9qNnRRN3BlSkt6bEJvTEQrSmR5NG5sMzVYblFOR0xuVkd6a3BwSkNaQUYrd2x0RzVkek95WE5aRXBBVmtaZHladk53VjJuRE85SHluelI0VnpSYkdiUFdOOUMwWGRIQm1RdnlMK2o1cG9TcW9HMWFSdm00N0MyOFNZYWV4WWdtWWdjSEVkQ3RocVlNbGxpeDg3S3o0RnhrS3QyTGFEWnVYdDE0VEVhUTZXcGhhQ3crRDYyYnN2cWd4VzNkMHM3cFlVeGdvU05UV2swY3UweWk3SXNpN21RUmpoREllOWpZa2hXdzJYVXlyTHFXb2N2aEdiVXF5WHFTY1RCSEVuU01aSkhsZEV2R3FkM1FGTENXdEh0YjVOWGd2K3lHaUZUckd0MWtMd3ZkV2d1NXhPaHIvdkUzeWpHVytONDBVRGY5WDhDQTFVZjlsZk9TVVQ5RkxscWsrUlhmTnVUcVg1UHRIQ0taMk0zeEpHNE5sSDBpZDFMbWxsalM0UmJmNTJreTFEdHVTN3BXYXROOWlVeUZMdWxSelJEUFk2OW1Lby9mbmM4Y0Y4NGhnZmJoWHJ6NktOeW1NZWM0L3kxQ0hyM1JDR2g5aDNZSjA5WFR4MmMwSlN5L09BVU8vY1BOWjg4VTY0Tk9NMTN0S0QzRStoL1RKRVZBVVREVUxaak1idDlsYnJIWlQ3dGFadm4zSFBwSXd6aG9TYy93dU9xckVKd0c5MDVMZENkbTY0Q3JWdTltanF2SE9IdWNZR044M2Vob0dXM3doRHIyLzZHK29COHI0TnRtaFE9IiwiY29tLmhwLmNsb3VkOnJvbGU6Uk9MRV9SRVNUIjp0cnVlLCJjb20uaHAuY2xvdWQ6cm9sZTpTVVBQT1JUIjp0cnVlLCJjb20uaHAuY2xvdWQ6cm9sZTpTVUJTQ1JJUFRJT05fQURNSU4iOnRydWUsImV4cCI6MTQ1ODExMjExNCwiaWF0IjoxNDU4MTEwMzE0LCJjb20uaHAuY2xvdWQ6cm9sZTpJRE1fQURNSU4iOnRydWV9.Y1qJG1iap2yJ8lL-hRO5Q5HXBVKEIdSWmO7T9zkRTQs";
	             //设置http头 消息
	             connection.setRequestProperty("Authorization","Basic aWRtVHJhbnNwb3J0VXNlcjppZG1UcmFuc3BvcnRVc2Vy");
	             connection.setRequestProperty("Content-Type","application/json");  //设定 请求格式 json，也可以设定xml格式的 
	             connection.setRequestProperty("Accept","application/json");//设定响应的信息的格式为 json，也可以设定xml格式的  
	             connection.setRequestProperty("X-Auth-Token",X_Auth_Token);  //特定http服务器需要的信息，根据服务器所需要求添加  
	             connection.connect();  
	   
	             //添加 请求内容
	            JSONObject user = new JSONObject();  
	            user.put("username", "orgadmin");
	            user.put("password", "propel");//仔细啊！！

	            //构建嵌套的 json数据
	             JSONObject obj = new JSONObject();
	             obj.put("passwordCredentials",user);
	             obj.put("tenantName", "CONSUMER");
	             
	             OutputStream out = connection.getOutputStream();
	             out.write(obj.toString().getBytes());  
	             out.flush();  
	             out.close();  
	   
	             System.out.println("" + connection.getResponseCode());
	     		
//	           		 读取响应  
	             BufferedReader reader = new BufferedReader(new InputStreamReader(  
	                     connection.getInputStream()));  
	             String lines;  
	             StringBuffer sb = new StringBuffer("");  
	             while ((lines = reader.readLine()) != null) {  
	                 lines = new String(lines.getBytes(), "utf-8");  
	                 sb.append(lines);  
	             }  
	             System.out.println(sb);  
	             reader.close();  
//    	              断开连接  
	             connection.disconnect();  
	         } catch (MalformedURLException e) {  
	             // TODO Auto-generated catch block  
	             e.printStackTrace();  
	         } catch (UnsupportedEncodingException e) {  
	             // TODO Auto-generated catch block  
	             e.printStackTrace();  
	         } catch (IOException e) {
	             // TODO Auto-generated catch block  
	             e.printStackTrace();
	         }
	   
	    }
	  
	    
	    private static void trustAllHosts() throws NoSuchAlgorithmException, KeyManagementException {
		    TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
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
