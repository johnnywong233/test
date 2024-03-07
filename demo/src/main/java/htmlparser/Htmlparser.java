package htmlparser;

import lombok.extern.slf4j.Slf4j;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class Htmlparser {
    //http://www.jb51.net/article/48988.htm
    public static void testHtml() {
        try {
            StringBuilder sTotalString = new StringBuilder();
            URL url = new URL("https://baidu.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream urlStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream));
            String sCurrentLine;
            while ((sCurrentLine = reader.readLine()) != null) {
                sTotalString.append(sCurrentLine).append("/r/n");
                //  System.out.println(sTotalString);
            }
            String testText = extractText(sTotalString.toString());
            System.out.println(testText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String extractText(String inputHtml) throws Exception {
        StringBuilder text = new StringBuilder();
        Parser parser = Parser.createParser(new String(inputHtml.getBytes(), "GBK"), "GBK");
        // 遍历所有的节点
        NodeList nodes = parser.extractAllNodesThatMatch((NodeFilter) node -> true);
        System.out.println(nodes.size()); //打印节点的数量
        for (int i = 0; i < nodes.size(); i++) {
            Node nodet = nodes.elementAt(i);
            //System.out.println(nodet.getText());
            text.append(new String(nodet.toPlainTextString().getBytes("GBK"))).append("/r/n");
        }
        return text.toString();
    }

    public static void test5(String resource) throws Exception {
        Parser myParser = new Parser(resource);
        myParser.setEncoding("GBK");
        String filterStr = "table";
        NodeFilter filter = new TagNameFilter(filterStr);
        NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
        TableTag tableTag = (TableTag) nodeList.elementAt(0);
        log.info("tableTag:{}", tableTag);
    }

    public static void main(String[] args) throws Exception {
        test5("https://www.google.com");
        testHtml();
    }
}
