package file.xml.xom;

import nu.xom.Document;
import nu.xom.Element;
import org.junit.Test;

/**
 * Author: Johnny
 * Date: 2016/12/10
 * Time: 23:44
 */
public class XomDemo {

    @Test
    public void generateXml() {
        Element root = new Element("root");
        root.appendChild("Hello World!");
        //root.appendChild("\n  Hello World!\n");
        Document doc = new Document(root);
        doc.toXML();
    }
}
