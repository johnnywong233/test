package file.xml;

/** 
 * @author johnny
 * define the interface for XML create and parse method
 */
public interface XmlDocument {
	/**  
	 * create XML document
	 * @param fileName   
	 */
	void createXml(String fileName);
	/** 
	 * parse XML document  
	 * @param fileName  
	 */
	void parserXml(String fileName);
}
