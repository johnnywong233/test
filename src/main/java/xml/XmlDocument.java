package xml;

/** 
 * @author johnny
 * define the interface for XML create and parse method
 */
public interface XmlDocument {
	/**  
	 * create XML document
	 * @param fileName   
	 */   
	public void createXml(String fileName);   
	/** 
	 * parse XML document  
	 * @param fileName  
	 */   
	public void parserXml(String fileName);   
}
