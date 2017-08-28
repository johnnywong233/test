package htmlparser;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

/**
 * Author: Johnny
 * Date: 2016/12/4
 * Time: 22:56
 */
public class GetLinkTest {
    //http://www.jb51.net/article/48329.htm
    public static void main(String[] args) {
        try {
            // 通过过滤器过滤出<A>标签
            Parser parser = new Parser("http://www.mzitu.com/100462");
            NodeList nodeList = parser.extractAllNodesThatMatch((NodeFilter) node -> {
                // 标记
                return node instanceof LinkTag;
            });
            // 打印
            for (int i = 0; i < nodeList.size(); i++) {
                LinkTag n = (LinkTag) nodeList.elementAt(i);
                //System.out.print(n.getStringText() + " ==>> ");
                //System.out.println(n.extractLink());
                try {
                    if (n.extractLink().contains("http://www.mzitu.com")) {
                        System.out.println(n.extractLink());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
