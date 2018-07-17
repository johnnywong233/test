package file.xml;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileReader;


public class TargetsXML {
    /*
     * http://www.ibm.com/developerworks/cn/java/j-5things12/index.html
	 */
    public static void main(String[] args) throws Exception {
        for (String arg : args) {
            XMLEventReader xsr = XMLInputFactory.newInstance().createXMLEventReader(new FileReader(arg));
            while (xsr.hasNext()) {
                XMLEvent evt = xsr.nextEvent();
                switch (evt.getEventType()) {
                    case XMLEvent.START_ELEMENT: {
                        StartElement se = evt.asStartElement();
                        if ("target".equals(se.getName().getLocalPart())) {
                            Attribute targetName = se.getAttributeByName(new QName("name"));
                            // Found a target!
                            System.out.println(targetName.getValue());
                        }
                        break;
                    }
                    // Ignore everything else
                }
            }
        }
    }

}