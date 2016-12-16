package file.xml;

import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestXML {
	
	public static void main(String[] args) {
		Document doc;
		DocumentBuilderFactory factory;
		DocumentBuilder docBuilder;
		
		Element root;
		FileInputStream in;
		String fileName;
		try{
				
			//get the xml file
//			fileName = System.getProperty("user.dir");
//			fileName = fileName+"/sample.xml";
			fileName = "C:\\Users\\wajian\\Documents\\Test\\sample.xml";
			in = new FileInputStream(fileName);
			
			factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			docBuilder = factory.newDocumentBuilder();
			doc = docBuilder.parse(in);
			System.out.println("parse successfull");
			
			root = doc.getDocumentElement();
			printAttributes(root);
			
			System.out.println("��ӡȫ���ڵ�");
			printElement(root,0);
				
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
	}
		
	public static void printAttributes(Element elem){
		NamedNodeMap attributes;
		int i,max;
		String name,value;
		Node curNode;
		
		attributes = elem.getAttributes();
		max = attributes.getLength();
		
		for(i=0;i<max;i++){
			curNode = attributes.item(i);
			name = curNode.getNodeName();
			value = curNode.getNodeValue();
			System.out.println("\t"+name+" = "+value);
		}
	}
		
	public static void printElement(Element elem,int depth){
		NodeList children;
		int i,max;
		Node curChild;
		Element curElement;
		String nodeName,nodeValue;
		
		//elementName = elem.getNodeName();
		children = elem.getChildNodes();
		
		for(int j=0;j<depth;j++){
			System.out.print(" ");
		}
		printAttributes(elem);
		
		max = children.getLength();
		for(i=0;i<max;i++){
		
			curChild = children.item(i);
			
			if(curChild instanceof Element){
				curElement = (Element)curChild;
				printElement(curElement,depth+1);
			} else{
				nodeName = curChild.getNodeName();
				nodeValue = curChild.getNodeValue();
				
				for(int j=0;j<depth;j++)
					System.out.print(" ");
				System.out.println(nodeName+" = "+nodeValue);
			}
		}
	}
}
