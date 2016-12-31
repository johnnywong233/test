package json;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class TestGetJson {

    public static void main(String[] args) {
        String url = "test.com";
        JSONObject params = new JSONObject();
        params.put("SRC_STM_CODE", "wsf");
        params.put("TENANT_ID", "123");
        params.put("NM", "mm");
        params.put("BRTH_DT", "1983-01-20");
        params.put("GND_CODE", "1");
        JSONArray params2 = new JSONArray();
        JSONObject param3 = new JSONObject();
        param3.put("DOC_TP_CODE", "10100");
        param3.put("DOC_NBR", "100200198301202210");
        param3.put("DOC_CUST_NM", "test");
        params2.add(param3);
        params.put("DOCS", params2);
        String ret = doPost(url, params).toString();
        System.out.println(ret);
    }

    /**
     * httpClient
     *
     * @return
     * @throws Exception
     */
    public static String doGet(String url, String charset)
            throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 2000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        String response = "";
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("error: " + getMethod.getStatusLine());
            }
            Header[] headers = getMethod.getResponseHeaders();
            for (Header h : headers)
                System.out.println(h.getName() + "------------ " + h.getValue());
            byte[] responseBody = getMethod.getResponseBody();
            response = new String(responseBody, charset);
            System.out.println("----------response:" + response);
            // InputStream response = getMethod.getResponseBodyAsStream();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return response;
    }

    /**
     * post
     *
     * @param url
     * @param json
     * @return
     */
    @SuppressWarnings({ "resource" })
	public static JSONObject doPost(String url, JSONObject json) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.fromObject(result);
                System.out.println(entity.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }


}
