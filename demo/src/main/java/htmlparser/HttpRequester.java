package htmlparser;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

/**
 * Author: Johnny
 * Date: 2016/12/1
 * Time: 0:14
 */
@Data
public class HttpRequester {
    private String defaultContentEncoding;

    public HttpRequester() {
        this.defaultContentEncoding = Charset.defaultCharset().name();
    }

    public HttpResponse sendGet(String urlString) throws IOException {
        return this.send(urlString, "GET", null, null);
    }

    public HttpResponse sendGet(String urlString, Map<String, String> params)
            throws IOException {
        return this.send(urlString, "GET", params, null);
    }

    public HttpResponse sendGet(String urlString, Map<String, String> params,
                                Map<String, String> propertys) throws IOException {
        return this.send(urlString, "GET", params, propertys);
    }

    public HttpResponse sendPost(String urlString) throws IOException {
        return this.send(urlString, "POST", null, null);
    }

    public HttpResponse sendPost(String urlString, Map<String, String> params)
            throws IOException {
        return this.send(urlString, "POST", params, null);
    }

    public HttpResponse sendPost(String urlString, Map<String, String> params,
                                 Map<String, String> propertys) throws IOException {
        return this.send(urlString, "POST", params, propertys);
    }

    private HttpResponse send(String urlString, String method,
                              Map<String, String> parameters, Map<String, String> propertys)
            throws IOException {
        HttpURLConnection urlConnection;

        if ("GET".equalsIgnoreCase(method) && parameters != null) {
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(parameters.get(key));
                i++;
            }
            urlString += param;
        }
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);

        if (propertys != null) {
            for (String key : propertys.keySet()) {
                urlConnection.addRequestProperty(key, propertys.get(key));
            }
        }

        if ("POST".equalsIgnoreCase(method) && parameters != null) {
            StringBuilder param = new StringBuilder();
            for (String key : parameters.keySet()) {
                param.append("&");
                param.append(key).append("=").append(parameters.get(key));
            }
            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }
        return this.makeContent(urlString, urlConnection);
    }

    private HttpResponse makeContent(String urlString,
                                     HttpURLConnection urlConnection) throws IOException {
        HttpResponse httpResponser = new HttpResponse();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(in));
            httpResponser.setContentCollection(new Vector<>());
            StringBuilder temp = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                httpResponser.getContentCollection().add(line);
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String ecod = urlConnection.getContentEncoding();
            if (ecod == null) {
                ecod = this.defaultContentEncoding;
            }
            httpResponser.setUrlString(urlString);
            httpResponser.setDefaultPort(urlConnection.getURL().getDefaultPort());
            httpResponser.setFile(urlConnection.getURL().getFile());
            httpResponser.setHost(urlConnection.getURL().getHost());
            httpResponser.setPath(urlConnection.getURL().getPath());
            httpResponser.setPort(urlConnection.getURL().getPort());
            httpResponser.setProtocol(urlConnection.getURL().getProtocol());
            httpResponser.setQuery(urlConnection.getURL().getQuery());
            httpResponser.setRef(urlConnection.getURL().getRef());
            httpResponser.setUserInfo(urlConnection.getURL().getUserInfo());

            httpResponser.setContent(new String(temp.toString().getBytes(), ecod));
            httpResponser.setContentEncoding(ecod);
            httpResponser.setCode(urlConnection.getResponseCode());
            httpResponser.setMessage(urlConnection.getResponseMessage());
            httpResponser.setContentType(urlConnection.getContentType());
            httpResponser.setMethod(urlConnection.getRequestMethod());
            httpResponser.setConnectTimeout(urlConnection.getConnectTimeout());
            httpResponser.setReadTimeout(urlConnection.getReadTimeout());

            return httpResponser;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

}
