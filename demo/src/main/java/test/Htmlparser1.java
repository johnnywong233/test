package test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;

public class Htmlparser1 {
    //http://www.jb51.net/article/46762.htm
    public static void main(String args[]) {
        List<Mp3> mp3List = new ArrayList<Mp3>();
        //TODO replace with a meaningful url
        String url = "http://www.jb51.net";
        String htmlStr = utils.ParserUtil.getContentFromUrl(url);
        try {
            Parser parser = new Parser(htmlStr);
            //这个地方我写的是提前获取好的html文本。也可以传入URl对象
            parser.setEncoding("utf-8");//设置编码机
            AndFilter filter =
                    new AndFilter(
                            new TagNameFilter("div"),
                            new HasAttributeFilter("id", "songListWrapper")
                    );//通过filter找到div且div的id为songListWrapper
            NodeList nodes = parser.parse(filter);//通过filter获取nodes
            Node node = nodes.elementAt(0);
            NodeList nodesChild = node.getChildren();
            Node[] nodesArr = nodesChild.toNodeArray();
            NodeList nodesChild2 = nodesArr[1].getChildren();
            Node[] nodesArr2 = nodesChild2.toNodeArray();
            Node nodeul = nodesArr2[1];
            Node[] nodesli = nodeul.getChildren().toNodeArray();


            for (int i = 2; i < nodesli.length; i++) {
                //System.out.println(nodesli[i].toHtml());
                Node tempNode = nodesli[i];
                TagNode tagNode = new TagNode();//通过TagNode获得属性，只有将Node转换为TagNode才能获取某一个标签的属性
                tagNode.setText(tempNode.toHtml());
                String claStr = tagNode.getAttribute("class");//claStr为bb-dotimg clearfix  song-item-hook { 'songItem': { 'sid': '113275822', 'sname': '我的要求不算高', 'author': '黄渤' } }
                claStr = claStr.replaceAll(" ", "");
                if (claStr.indexOf("\\?") == -1) {
                    Pattern pattern = Pattern.compile("[\\s\\wa-z\\-]+\\{'songItem':\\{'sid':'([\\d]+)','sname':'([\\s\\S]*)','author':'([\\s\\S]*)'\\}\\}");
                    Matcher matcher = pattern.matcher(claStr);
                    if (matcher.find()) {
                        Mp3 mp3 = new Mp3();
                        mp3.setSid(matcher.group(1));
                        mp3.setSname(matcher.group(2));
                        mp3.setAuthor(matcher.group(3));
                        mp3List.add(mp3);
                        //for(int j=1;j<=matcher.groupCount();j++){
                        //System.out.print("   "+j+"--->"+matcher.group(j));
                        //}
                    }
                }
                //System.out.println(matcher.find());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
