package file.xml.jdom;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2016/12/18
 * Time: 21:18
 * Description: while there are multi struts-config-xxx.xml files, ActionServlet will process them one by one;
 * here, combine several xml files then process the temp document; make sure them are identical in format
 */
public class CombineXML {

    public static void main(String[] args) throws Exception {
        Document document;
        try {
            SAXBuilder dbf = new SAXBuilder();
            document = dbf.build(args[0]);
            Element docRoot = document.getRootElement();

            for (int i = 1; i < args.length; i++) {
                Document tmpDoc = dbf.build(args[i]);
                List<Element> nlt = tmpDoc.getRootElement().getChildren();
                for (int j = 0; j < nlt.size(); ) {
                    Element el = nlt.get(0);
                    // get free element
                    el.detach();
                    docRoot.addContent(el);
                }
            }
        } catch (IOException e) {
            throw new Exception("File not readable.");
        } catch (JDOMException e) {
            throw new Exception("File parsed error.");
        }
    }
}
