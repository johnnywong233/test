package htmlparser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

/*
 * 用于JAVA中的HTTP请求，htmlloader用于发送HTTP请求并返回文档对象<br>可以结合htmlparse组件完成对HTML文档的解析
 */
import com.yao.http.HttpRequester;
import com.yao.http.HttpRespons;

//TODO: rewrite HttpRespons and HttpRequester
public class HtmlParserDemo {
    public static void main(String[] args) {
        try {
/* 使用HttpRequester类和HttpRespons类获得一个HTTP请求中的数据（HTML文档）。 htmlloader库中有上述类；
 * 《JAVA发送HTTP请求，返回HTTP响应内容，实例及应用》一文中摘取上述两JAVA类的代码。
*/
//            Map<String, String> map = new HashMap<String, String>();
            HttpRequester request = new HttpRequester();
            HttpRespons hr = request.sendGet("http://www.baidu.com");

            Parser parser = Parser.createParser(hr.getContent(), hr.getContentEncoding());
            try {
                // 通过过滤器过滤出<A>标签
                NodeList nodeList = parser
                        .extractAllNodesThatMatch(new NodeFilter() {
                            private static final long serialVersionUID = 1L;

                            //实现该方法,用以过滤标签
                            public boolean accept(Node node) {
                                return node instanceof LinkTag;
                            }
                        });
                //print
                for (int i = 0; i < nodeList.size(); i++) {
                    LinkTag n = (LinkTag) nodeList.elementAt(i);
                    System.out.print(n.getStringText() + " ==>> ");
                    System.out.println(n.extractLink());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}