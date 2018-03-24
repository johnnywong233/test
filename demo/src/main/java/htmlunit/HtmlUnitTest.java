package htmlunit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

/**
 * Created by Johnny on 2018/3/18.
 */
public class HtmlUnitTest {
    public static void main(String[] args) {
        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            HtmlPage page = webClient.getPage("http://pic.sogou.com/d?query=%D5%C5%D1%A7%D3%D1&mode=1&did=8"); // 解析获取页面
            HtmlImage image = (HtmlImage) page.getByXPath("//div[@id=\"thumbBox\"]/a[@id=\"imageBox\"]/img").get(0);
            String attribute = image.getAttribute("src");
            System.out.println(attribute);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("网页html:" + page.asXml()); // 获取Html
        } catch (FailingHttpStatusCodeException | IOException e) {
            e.printStackTrace();
        }
    }
}