package htmlparser;

import lombok.Data;

import java.util.Vector;

/**
 * Author: Johnny
 * Date: 2016/12/1
 * Time: 0:12
 */
@Data
public class HttpResponse {
    private String urlString;
    private int defaultPort;
    private String file;
    private String host;
    private String path;
    private int port;
    private String protocol;
    private String query;
    private String ref;
    private String userInfo;
    private String contentEncoding;
    private String content;
    private String contentType;
    private int code;
    private String message;
    private String method;
    private int connectTimeout;
    private int readTimeout;
    private Vector<String> contentCollection;
}
