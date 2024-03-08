package utils;

import org.dom4j.Node;

/**
 * Author: Johnny
 * Date: 2016/12/18
 * Time: 21:39
 */
public class XMLUtil {
    //inner xml do not contain nodes(left and right)
    public static String getInnerXML(Node node) {
        String nodeName = node.getName(); //get node name
        String outerXML = node.asXML(); //get outerXML
        //注意起始节点可能会带属性
        return outerXML.replaceAll("^<" + nodeName + ".*?>|" + nodeName + ">$", "");
    }

    public static void main(String[] args) {
    }

}
