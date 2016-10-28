package httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	/*
	 * http://blog.csdn.net/mr_tank_/article/details/17454315
	 * POST 怎么实现？
	 */
	public static void main(String args[]) {
		  //创建HttpClientBuilder
	    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
	    //HttpClient
	    CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

	    HttpGet httpGet = new HttpGet("http://www.gxnu.edu.cn/default.html");
	    System.out.println(httpGet.getRequestLine());
	    try {
	        //执行get请求
	        HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
	        //获取响应消息实体
	        HttpEntity entity = httpResponse.getEntity();
	        //响应状态
	        System.out.println("status:" + httpResponse.getStatusLine());
	        //判断响应实体是否为空
	        if (entity != null) {
	            System.out.println("content Encoding:" + entity.getContentEncoding());
	            System.out.println("response content:" + EntityUtils.toString(entity));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            //关闭流并释放资源
	            closeableHttpClient.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    main1(args);
	    
	}

	//TODO
	public static void main1(String args[]) {
//	    //创建HttpClientBuilder
//	    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//	    //HttpClient
//	    CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//	
//	    HttpPost httpPost = new HttpPost("http://tutor.sturgeon.mopaas.com/tutor/search");
//	    httpPost.setConfig(DEFAULT);
//	    // 创建参数队列
//	    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//	    formparams.add(new BasicNameValuePair("searchText", "英语"));
//	
//	    UrlEncodedFormEntity entity;
//	    try {
//	        entity = new UrlEncodedFormEntity(formparams, "UTF-8");
//	        httpPost.setEntity(entity);
//	
//	        HttpResponse httpResponse;
//	        //post请求
//	        httpResponse = closeableHttpClient.execute(httpPost);
//	
//	        //getEntity()
//	        HttpEntity httpEntity = httpResponse.getEntity();
//	        if (httpEntity != null) {
//	            //打印响应内容
//	            System.out.println("response:" + EntityUtils.toString(httpEntity, "UTF-8"));
//	        }
//	        //释放资源
//	        closeableHttpClient.close();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
	}
	
}