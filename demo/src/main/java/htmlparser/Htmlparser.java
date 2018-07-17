package htmlparser;

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

public class Htmlparser {
    //http://www.jb51.net/article/48988.htm
    public static void testHtml() {
        try {
            String sCurrentLine;
            String sTotalString;
            sTotalString = "";
            InputStream urlStream;
            URL url = new URL("http://www.ideagrace.com/html/doc/2006/07/04/00929.html");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            urlStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream));
            while ((sCurrentLine = reader.readLine()) != null) {
                sTotalString += sCurrentLine + "/r/n";
                //  System.out.println(sTotalString);
            }
            String testText = extractText(sTotalString);
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
            text.append(new String(nodet.toPlainTextString().getBytes("GBK")) + "/r/n");
        }
        return text.toString();
    }

    public static void test5(String resource) throws Exception {
        Parser myParser = new Parser(resource);
        myParser.setEncoding("GBK");
        String filterStr = "table";
        NodeFilter filter = new TagNameFilter(filterStr);
        NodeList nodeList = myParser.extractAllNodesThatMatch(filter);
        TableTag tabletag = (TableTag) nodeList.elementAt(11);
    }

    public static void main(String[] args) throws Exception {
        test5("http://www.google.com");
        testHtml();
    }
}
