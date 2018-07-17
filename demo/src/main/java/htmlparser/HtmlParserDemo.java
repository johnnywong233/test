package htmlparser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

/*
 * 用于JAVA中的HTTP请求，HttpRequester用于发送HTTP请求并返回文档对象<br>可以结合htmlparse组件完成对HTML文档的解析
 */

public class HtmlParserDemo {
    public static void main(String[] args) {
        try {
/* 使用HttpRequester类和HttpResponse类获得一个HTTP请求中的数据（HTML文档）。
*/
//            Map<String, String> map = new HashMap<String, String>();
            HttpRequester request = new HttpRequester();
            HttpResponse hr = request.sendGet("http://www.baidu.com");

            Parser parser = Parser.createParser(hr.getContent(), hr.getContentEncoding());
            try {
                // 通过过滤器过滤出<A>标签
                NodeList nodeList = parser
                        .extractAllNodesThatMatch(new NodeFilter() {
                            private static final long serialVersionUID = 1L;

                            //实现该方法,用以过滤标签
                            @Override
                            public boolean accept(Node node) {
                                return node instanceof LinkTag;
                            }
                        });
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