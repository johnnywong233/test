package jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by johnny on 2016/9/29.
 * demo of parser in jsoup
 * 使用DOM或CSS选择器来查找、取出数据；可操作HTML元素、属性、文本；
 */
public class ParserDemo {
    //https://my.oschina.net/u/1385143/blog/175069
    public static void main(String[] args) {
        ParserDemo t = new ParserDemo();
//        t.parseUrl();
//        t.parseString();
        t.parseFile();
    }

    private void parseString() {
        String html = "<html><head><title>blog</title></head><body onload='test()'><p>Parsed HTML into a doc.</p></body></html>";
        Document doc = Jsoup.parse(html);
        System.out.println("----------------");
        System.out.println("doc: \n" + doc);
        Elements es = doc.body().getAllElements();
        System.out.println(es.attr("onload"));
        System.out.println(es.select("p"));
    }

    private void parseUrl() {
        try {
            Document doc = Jsoup.connect("https://www.sina.com.cn/").get();
            String html = "<p>An <a href='https://example.com/'><b>example</b></a> link.</p>";
            //can also read html file
            Document doc1 = Jsoup.parse(html);//解析HTML字符串返回一个Document
            Elements hrefs = doc1.select("a[href]");
            System.out.println("hrefs：" + hrefs);
            System.out.println("------------------");
            System.out.println(hrefs.select("[href^=http]"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO
    private void parseFile() {
        try {
            File input = new File("D:\\Java_ex\\test\\demo\\src\\test\\resources\\index.html");
            Document doc = Jsoup.parse(input, "UTF-8");
            // 提取出所有的编号
//            Elements codes = doc.body().select("p");
            Elements codes = doc.select("");
            System.out.println(codes);
            System.out.println("------------------");
            System.out.println(codes.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}