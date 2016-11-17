package file.xml;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl;

//http://www.jb51.net/article/43715.htm
public class XMLDemo {

    //DOM
    @Test
    public void method1() {
        DocumentBuilder sb = null;
        try {
            sb = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document root = null;
        try {
            if (sb != null) {
                root = sb.parse(XMLDemo.class.getClassLoader().getResourceAsStream("NewFile.xml"));
            }
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        System.out.println(root.getChildNodes().item(0).getNodeName());
    }

    //SAX
    @Test
    public void method2() {
        SAXParserFactory factory = SAXParserFactoryImpl.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e1) {
            e1.printStackTrace();
        }
        try {
            assert parser != null;
            parser.parse(XMLDemo.class.getClassLoader().getResourceAsStream("NewFile.xml"),
                    new DefaultHandler() {
                        @Override
                        public void characters(char[] ch, int start, int length) throws SAXException {
                            System.out.println("characters");
                        }

                        @Override
                        public void endDocument() throws SAXException {
                            System.out.println("endDocument");
                        }

                        @Override
                        public void endElement(String uri, String localName, String qName) throws SAXException {
                            System.out.println("endElement");
                        }

                        @Override
                        public void startDocument() throws SAXException {
                            System.out.println("startDocument");
                        }

                        @Override
                        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                            System.out.println("startElement");
                        }
                    });
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    //XMLStream
    @Test
    public void method3() {
        XMLInputFactory xmlFactor = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        try {
            reader = xmlFactor.createXMLStreamReader(XMLDemo.class.getClassLoader().getResourceAsStream("NewFile.xml"));
        } catch (XMLStreamException e1) {
            e1.printStackTrace();
        }
        try {
            assert reader != null;
            while (reader.hasNext()) {
                int point = reader.next();
                switch (point) {
                    case XMLStreamReader.START_ELEMENT:
                        System.out.println("start_element");
                    case XMLStreamReader.END_ELEMENT:
                        // do something...
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    //DOM4j XPATH
    @Test
    public void method4() {
        SAXReader reader = new SAXReader();
        org.dom4j.Document root = null; // only through this way to use document
        try {
            root = reader.read(XMLDemo.class.getClassLoader().getResourceAsStream("NewFile.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        /* 选择所有的cc节点 */
        if (root != null) {
            System.out.println(root.selectNodes("//cc").size());
            /*选择所有的book节点，并且有子节点author的*/
            System.out.println((root.selectNodes("//book[author]").size()));
			/* 选择所有book节点，并且有属性category的   */
            System.out.println((root.selectNodes("//book[@category]").size()));
			/* 选择所有book节点，并且有子节点author值为James McGovern ，并且还有category属性节点值为WEB   下面的price节点*/
            System.out.println(root.selectNodes("//book[author='James McGovern'][@category='WEB']/price").size());
        }
    }
}
