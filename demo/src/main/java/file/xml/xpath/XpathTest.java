package file.xml.xpath;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by johnny on 2016/9/7.
 * demo usage of xml.xpath to parse xml file
 */
public class XpathTest {

    //http://www.jb51.net/article/37862.htm
    public static void main(String[] args) throws ParserConfigurationException,
            SAXException, IOException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("D:\\Java_ex\\test\\src\\test\\resources\\xpath.xml");
        System.out.println("length of node:" + doc.getChildNodes().getLength());

        XPathFactory xFactory = XPathFactory.newInstance();
        XPath xpath = xFactory.newXPath();
        //if the xml to be parsed has defined namespace
//        xpath.setNamespaceContext(new CustomNamespaceContext());
        XPathExpression expr = xpath.compile("/bookstore/book");//  //name/text()
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        System.out.println("length of node:" + nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            Element eElement = (Element) nodes.item(i);
            System.out.println(nodes.item(i).getNodeName());
//            System.out.println(nodes.item(i).getNodeValue());
            System.out.println("title:" + eElement.getElementsByTagName("title").item(0).getTextContent());
            System.out.println("price:" + eElement.getElementsByTagName("price").item(0).getTextContent());
        }
    }
}

//self-defined namespace context
class CustomNamespaceContext implements NamespaceContext {
    @Override
    public String getNamespaceURI(String prefix) {
        switch (prefix) {
            case "ns":
                return "https://www.tibco.com/cdc/liugang/ns";
            case "tg":
                return "https://www.tibco.com/cdc/liugang/tg";
            case "df":
                return "https://www.tibco.com/cdc/liugang";
            default:
        }
        //eclipse IDE error can be ignored
        return XMLConstants.NULL_NS_URI;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        return null;
    }
}