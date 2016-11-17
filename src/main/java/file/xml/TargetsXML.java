package file.xml;

import java.io.FileReader;

import javax.xml.namespace.QName;
import javax.xml.stream.events.*;
import javax.xml.stream.*;


public class TargetsXML {
	/*
	 * http://www.ibm.com/developerworks/cn/java/j-5things12/index.html
	 */
	
    @SuppressWarnings("restriction")
	public static void main(String[] args) throws Exception{
            for (String arg : args){
                XMLEventReader xsr = XMLInputFactory.newInstance().createXMLEventReader(new FileReader(arg));
                while (xsr.hasNext()){
                    XMLEvent evt = xsr.nextEvent();
                    switch (evt.getEventType()){
                        case XMLEvent.START_ELEMENT:{
                            StartElement se = evt.asStartElement();
                            if (se.getName().getLocalPart().equals("target")){
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
