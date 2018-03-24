package htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;

import java.io.IOException;

/**
 * Created by Johnny on 2018/3/18.
 */
public class SimpleConnectionListener extends FalsifyingWebConnection {

    SimpleConnectionListener(WebClient webClient) throws IllegalArgumentException {
        super(webClient);
    }

    @Override
    public WebResponse getResponse(WebRequest request) throws IOException {
        WebResponse response = super.getResponse(request);
        String url = response.getWebRequest().getUrl().toString();
        System.out.println("请求资源地址：======》" + url);
        return response;
    }
}