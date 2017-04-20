package utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import javax.annotation.PostConstruct;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/10/26
 * Time: 23:10
 */
public class HttpClientUtil {
    protected static Log log = LogFactory.getLog(HttpClientUtil.class);

    private CloseableHttpClient httpClient;

    protected static HttpClient httpclient = null;
    protected static int maxTotal = 200;
    protected static int maxPerRoute = 20;
    protected static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";

    //TODO: deprecated method/api replace
    static {
        if (httpclient == null) {
            // create httpclient
            SchemeRegistry reg = new SchemeRegistry();
            reg.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
            reg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
            ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(reg);
            cm.setMaxTotal(maxTotal);
            cm.setDefaultMaxPerRoute(maxPerRoute);
            httpclient = new DefaultHttpClient(cm);
        }
    }

    @PostConstruct
    public void init(){
        try {
            this.createClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
	public CloseableHttpClient createClient() throws Exception {
        if (httpClient == null) {
            try {
                httpClient = HttpClients.custom().
                        setHostnameVerifier(new AllowAllHostnameVerifier()).
                        setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build()).build();
            } catch (Exception e) {
                throw new RuntimeException("Can't build SSL ignored http client");
            }
        }
        return httpClient;
    }

    /**
     * 以@InputStream 形式下载URL资源
     */
    public static InputStream downloadAsStream(String url) throws Exception {
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
     * @return 如果saveFile==null, 则转换为@InputStream 否则save as @File
     * @throws Exception
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
            response = httpclient.execute(request);
            entity = response.getEntity();
            entity = new BufferedHttpEntity(entity);
            // get result
            if (saveToFile) {// save to disk
                fos = new FileOutputStream(saveFile);
                IOUtils.copy(entity.getContent(), fos);
                result = saveFile;
            } else { // warp to inpustream
                result = new BufferedInputStream(entity.getContent());
            }
        } catch (Exception e) {
            err = e;
        } finally {
            // close
            IOUtils.closeQuietly(fos);
            // clear
            request = null;
            response = null;
            entity = null;
            if (err != null) {
                throw err;
            }
            return result;
        }
    }

    protected static void addHeaderAndParams(final HttpRequestBase request, final Map<String, String> params) {
        // add default header
        request.addHeader("User-Agent", userAgent);
        // add params
        if (params != null) {
            // map --> HttpParams
            BasicHttpParams myParams = new BasicHttpParams();
            for (String key : params.keySet()) {
                myParams.setParameter(key, params.get(key));
            }
            request.setParams(myParams);
        }
    }

    public static HttpClient getHttpclient() {
        return httpclient;
    }

    public static void setHttpclient(HttpClient httpclient) {
        HttpClientUtil.httpclient = httpclient;
    }

    public static int getMaxTotal() {
        return maxTotal;
    }

    public static void setMaxTotal(int maxTotal) {
        HttpClientUtil.maxTotal = maxTotal;
    }

    public static int getMaxPerRoute() {
        return maxPerRoute;
    }

    public static void setMaxPerRoute(int maxPerRoute) {
        HttpClientUtil.maxPerRoute = maxPerRoute;
    }

    public static String getUserAgent() {
        return userAgent;
    }

    public static void setUserAgent(String userAgent) {
        HttpClientUtil.userAgent = userAgent;
    }
}