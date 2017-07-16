package jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 21:11
 */
public class TestDocument {
    //http://blog.csdn.net/earbao/article/details/41447107
    public static void main(String[] args) throws Exception {
        parseBodyFragment();
        parserHTML();
        parseGmail();
        download();
        parserFromFile();
        parseLink();
        visitDom();
        parserURL();
        cleaner();
        setContent();

        String html = "<span class=\"yP\" email=\"542335496@qq.com\" name=\"為妳變┳乖\"> - 為妳變┳乖</span>";
        html = unhtml(html);
        Document doc = Jsoup.parse(html);
        Element span = doc.select("span").first();
        String input = span.text();
        System.out.println(input);
        System.out.println(span.attr("email"));
        // <span class="y2"> - 您好，邮件我已经收到，我会尽快给您回复。祝你学习进步，
        // 工作顺利！</span>
        // <span title="2014年10月16日 下午6:06" id=":3s"
        // aria-label="2014年10月16日 下午6:06">10月16日</span>
    }

    public static String html(String content) {
        if (content == null)
            return "";
        String html = content;
        // html = html.replace( "'", "'");
        html = html.replaceAll("&", "&");
        html = html.replace("\"", "\""); //"
        html = html.replace("\t", "  ");// 替换跳格
        html = html.replace(" ", " ");// 替换空格
        html = html.replace("<", "<");
        html = html.replaceAll(">", ">");
        return html;
    }

    public static String unhtml(String content) {
        if (content == null)
            return "";
        String html = content;
        html = html.replaceAll("&", "&");
        html = html.replace("\"", "\"");
        html = html.replace("  ", "\t");// 替换跳格
        html = html.replace("- ", " ");// 替换空格
        html = html.replace(" ", " ");// 替换空格
        html = html.replace("<", "<");
        html = html.replaceAll(">", ">");
        return html;
    }

    private static void setContent() {
        String html = "<p>An <a href='http://example.com/'><b>example</b><div>test</div></a><span>字体</span> <li><li>link.</p>";
        Document doc = Jsoup.parse(html);
        Element div = doc.select("div").first(); // <div></div>
        div.html("<p>lorem ipsum</p>"); // <div><p>lorem ipsum</p></div>
        div.prepend("<p>First</p>");// 在div前添加html内容
        div.append("<p>Last</p>");// 在div之后添加html内容
        // 添完后的结果: <div><p>First</p><p>lorem ipsum</p><p>Last</p></div>

        Element span = doc.select("span").first(); // <span>One</span>
        span.wrap("<li><a href='http://example.com/'></a></li>");
        // 添完后的结果: <li><a href="http://example.com"><span>One</span></a></li>

        Element div2 = doc.select("li").first(); // <div></div>
        div2.text("five > four"); // <div>five > four</div>
        div2.prepend("First ");
        div2.append(" Last");

        doc.select("div.masthead").attr("title", "jsoup").addClass("round-box");

        System.out.println(doc);
    }

    private static void cleaner() {
        String unsafe = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
        String safe = Jsoup.clean(unsafe, Whitelist.basic());
        System.out.println(safe);
        // now: <p><a href="http://example.com/" rel="nofollow">Link</a></p>f
    }

    private static void parserURL() throws Exception {
        Document doc = Jsoup.connect("http://www.open-open.com/").get();

        Element link = doc.select("a").first();
        String relHref = link.attr("href"); // == "/"
        String absHref = link.attr("abs:href"); // "http://www.open-open.com/"
        System.out.println(relHref);
        System.out.println(absHref);
    }



    private static void visitDom() throws Exception {
        File input = new File("d:/login.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://www.baidu.com/");

        Element content = doc.getElementById("body");
        Elements links = content.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            System.out.println(linkHref);
            System.out.println(linkText);
        }
    }

    private static void parseLink() {
        String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
        Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现
        Element link = doc.select("a").first();//查找第一个a元素

        String text = doc.body().text();//取得字符串中的文本
        String linkHref = link.attr("href");//取得链接地址
        String linkText = link.text();//取得链接地址中的文本

        String linkOuterH = link.outerHtml();
        // "<a href="http://example.com"><b>example</b></a>"
        String linkInnerH = link.html();//取得链接内的html内容
        System.out.println(text);
        System.out.println(linkHref);
        System.out.println(linkText);
        System.out.println(linkOuterH);
        System.out.println(linkInnerH);
    }

    private static void parserFromFile() throws Exception {
        File input = new File("d:/login.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://www.baidu.com/");
        System.err.println(doc);
    }

    private static void download() throws Exception {
        Document doc = Jsoup.connect("http://www.baidu.com/").data("query",
                "Java").userAgent("Mozilla").cookie("auth", "token").timeout(
                3000).get();
        System.out.println(doc);
    }

    private static void parserHTML() {
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        Document doc = Jsoup.parse(html);
        System.out.println(doc);
    }

    private static void parseGmail() throws Exception {
        Document doc = Jsoup
                .connect("https://accounts.google.com/ServiceLogin").get();
        Element content = doc.getElementById("gaia_loginform");
        // System.out.println(content);

        Elements inputs = content.select("input[name]");
        // StringBuffer sb=new StringBuffer();
        Map<String, String> maps = new HashMap<String, String>();
        for (Element element : inputs) {
            // System.out.println(element);
            String name = element.attr("name");
            String value = element.attr("value");
            // System.out.println(name+"="+value);
            if (value != null && !"".equals(value)) {
                maps.put(name, value);
            }

        }
        // Email= Passwd=
        System.out.println(maps);
    }

    // 解析body片段
    private static void parseBodyFragment() {
        String html = "<div><p>Lorem ipsum.</p>";
        Document doc = Jsoup.parseBodyFragment(html);
        Element body = doc.body();

        System.out.println(body);
    }
}
