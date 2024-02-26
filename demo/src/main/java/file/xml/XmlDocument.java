package file.xml;

/**
 * @author johnny
 * define the interface for XML create and parse method
 */
public interface XmlDocument {
    /**
     * create XML document
     */
    void createXml(String fileName);

    /**
     * parse XML document
     */
    void parserXml(String fileName);
}
