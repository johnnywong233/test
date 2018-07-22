package utils;

import lombok.Data;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/10/26
 * Time: 23:10
 */
@Data
public class HttpClientUtil {
    protected static Log logger = LogFactory.getLog(HttpClientUtil.class);

    private static CloseableHttpClient closeableHttpClient;

    protected static HttpClient httpClient = null;
    protected static int maxTotal = 200;
    protected static int maxPerRoute = 20;
    protected static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";

    static {
        if (httpClient == null) {
            // create httpClient
//            SchemeRegistry reg = new SchemeRegistry();
//            reg.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
//            reg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
//            ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(reg);
//            cm.setMaxTotal(maxTotal);
//            cm.setDefaultMaxPerRoute(maxPerRoute);
//            httpClient = new DefaultHttpClient(cm);

            HttpClientBuilder builder = HttpClientBuilder.create();
            SSLContext context = null;
            SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
            builder.setSSLSocketFactory(sslConnectionFactory);

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslConnectionFactory).build();

            HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);

            builder.setConnectionManager(ccm);

            httpClient = builder.build();
        }
    }

    @PostConstruct
    public void init() {
        try {
            HttpClientUtil.createClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CloseableHttpClient createClient() throws Exception {
        if (closeableHttpClient == null) {
            try {
                closeableHttpClient = HttpClients.custom().setSSLHostnameVerifier
                        (new DefaultHostnameVerifier()).setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build()).build();
            } catch (Exception e) {
                throw new RuntimeException("Can't build SSL ignored http client");
            }
        }
        return closeableHttpClient;
    }

    @Test
    public void test1() throws Exception {
        downloadAsStream("");
    }

    /**
     * 以@InputStream 形式下载URL资源
     */
    static InputStream downloadAsStream(String url) throws Exception {
        return (InputStream) download(url, null, null, false);
    }

    /**
     * <pre>以@File 形式下载URL资源 </pre>
     */
    public static void download(String url, File saveFile) throws Exception {
        download(url, saveFile, null, false);
    }

    /**
     * <pre>下载</pre>
     *
     * @return 如果saveFile==null, 则转换为@InputStream 否则save as @File
     */
    public static Object download(final String url, final File saveFile, final Map<String, String> params,
                                  final boolean isPost) throws Exception {
        boolean saveToFile = saveFile != null;
        // check dir exist ?
        if (saveToFile && !saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        Exception err = null;
        HttpRequestBase request;
        HttpResponse response;
        HttpEntity entity;
        FileOutputStream fos = null;
        Object result = null;
        try {
            // create request
            if (isPost) {
                request = new HttpPost(url);
            } else {
                request = new HttpGet(url);
            }
            // add header & params
            addHeaderAndParams(request, params);
            // connect
            response = httpClient.execute(request);
            entity = response.getEntity();
            entity = new BufferedHttpEntity(entity);
            // get result
            if (saveToFile) {
                fos = new FileOutputStream(saveFile);
                IOUtils.copy(entity.getContent(), fos);
                result = saveFile;
            } else {
                // warp to inpustream
                result = new BufferedInputStream(entity.getContent());
            }
        } catch (Exception e) {
            err = e;
        } finally {
            // close
            IOUtils.closeQuietly(fos);
            // clear
            if (err != null) {
                throw err;
            }
            return result;
        }
    }

    private static void addHeaderAndParams(final HttpRequestBase request, final Map<String, String> params) {
        // add default header
        request.addHeader("User-Agent", userAgent);
        // add params
        if (params != null) {

            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.DEFAULT)
                    .setExpectContinueEnabled(true)
                    .setRelativeRedirectsAllowed(true)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC))
                    .build();
            RequestConfig config = RequestConfig.copy(defaultRequestConfig)
                    .setConnectionRequestTimeout(0)
                    .setConnectTimeout(0)
                    .setSocketTimeout(0)
                    .build();

            request.setConfig(config);
        }
    }


    public static String doGet(String url, String charset) throws Exception {

        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        GetMethod method = new GetMethod(url);

        if (null == url || !url.startsWith("http")) {
            throw new Exception("请求地址格式不对");
        }
        // 设置请求的编码方式
        if (null != charset) {
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + charset);
        } else {
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + "utf-8");
        }
        int statusCode = client.executeMethod(method);

        // 打印服务器返回的状态

        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("Method failed: " + method.getStatusLine());
        }
        // 返回响应消息
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
        // 在返回响应消息使用编码(utf-8或gb2312)
        String response = new String(responseBody, "utf-8");
        System.out.println("------------------response:" + response);
        method.releaseConnection();
        return response;
    }

    public static String doGet2(String url, String charset) throws Exception {
        /*
         * 使用 GetMethod 来访问一个 URL 对应的网页,实现步骤: 1:生成一个 HttpClinet 对象并设置相应的参数。
         * 2:生成一个 GetMethod 对象并设置响应的参数。 3:用 HttpClinet 生成的对象来执行 GetMethod 生成的Get
         * 方法。 4:处理响应状态码。 5:若响应正常，处理 HTTP 响应内容。 6:释放连接。
         */
        /* 1 生成 HttpClinet 对象并设置参数 */
        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        // 设置 Http 连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

        /* 2 生成 GetMethod 对象并设置参数 */
        GetMethod getMethod = new GetMethod(url);
        // 设置 get 请求超时为 5 秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // 设置请求重试处理，用的是默认的重试处理：请求三次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        String response = "";
        /* 3 执行 HTTP GET 请求 */
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            /* 4 判断访问的状态码 */
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
            /* 5 处理 HTTP 响应内容 */
            // HTTP响应头部信息，这里简单打印
            Header[] headers = getMethod.getResponseHeaders();
            for (Header h : headers) {
                System.out.println(h.getName() + "------------ " + h.getValue());
            }
            // 读取 HTTP 响应内容，这里简单打印网页内容
            // 读取为字节数组
            byte[] responseBody = getMethod.getResponseBody();
            response = new String(responseBody, charset);
            System.out.println("----------response:" + response);
            // 读取为 InputStream，在网页内容数据量大时候推荐使用
            // InputStream response = getMethod.getResponseBodyAsStream();

        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            /* 6 .释放连接 */
            getMethod.releaseConnection();
        }
        return response;
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url     请求的URL地址
     * @param params  请求的查询参数,可以为null
     * @param charset 字符集
     * @param pretty  是否美化
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, Object> params, String charset, boolean pretty) {

        StringBuilder response = new StringBuilder();
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        PostMethod method = new PostMethod(url);

        // 设置Http Post数据
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                method.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        // 设置Http Post数据  方法二
//        if(params != null) {
//            NameValuePair[] pairs = new NameValuePair[params.size()];//纯参数了，键值对
//            int i = 0;
//            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                pairs[i] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
//                i++;
//            }
//            method.addParameters(pairs);
//        }

        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                // 读取为 InputStream，在网页内容数据量大时候推荐使用
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),
                        charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(System.getProperty("line.separator"));
                    } else {
                        response.append(line);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("execute HTTP Post request" + url + "error occurred！");
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        System.out.println("--------------------" + response.toString());
        return response.toString();
    }

    /**
     * post请求
     *
     * @param url            url地址
     * @param jsonParam      参数
     * @param noNeedResponse 不需要返回结果
     */
    private static JSONObject httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) throws Exception {
        //post请求返回结果
        CloseableHttpClient httpClient = createClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /*请求发送成功，并得到响应*/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str;
                try {
                    /*读取服务器返回过来的json字符串数据*/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /*把json字符串转换成json对象*/
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * httpPost
     *
     * @param url       路径
     * @param jsonParam 参数
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) throws Exception {
        return httpPost(url, jsonParam, false);
    }

    public static JSONObject httpGet(String url) {
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            CloseableHttpClient client = createClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                /*读取服务器返回过来的json字符串数据*/
                String strResult = EntityUtils.toString(response.getEntity());
                /*把json字符串转换成json对象*/
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
}
