package file.xml;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wajian on 2016/8/13.
 */
public class DomXMLString {

    //http://www.jb51.net/article/67460.htm

    private static String SERVICES_HOST = "www.webxml.com.cn";
    //remote WebService interface URL
    private static String NETDATA_URL = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getRegionProvince";
    //the absolute path to save XML data get from remote WebService
    private static String LOCAL_PC_SAVEFILE_URL = "netDataToLocalFile.xml";

    private DomXMLString() {
    }

    public static void main(String[] args) throws Exception {
        Document document = getProvinceCode(NETDATA_URL);
        helloOK(document, LOCAL_PC_SAVEFILE_URL);
    }

    /**
     * return Document object
     */
    private static Document getProvinceCode(String netXMLDataURL) {
        Document document;
        DocumentBuilderFactory documentBF = DocumentBuilderFactory.newInstance();
        documentBF.setNamespaceAware(true);
        try {
            DocumentBuilder documentB = documentBF.newDocumentBuilder();
            InputStream inputStream = getSoapInputStream(netXMLDataURL);
            //webService
            document = documentB.parse(inputStream);
            inputStream.close();
        } catch (DOMException | IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            return null;
        }
        return document;
    }

    /**
     * return the InputStream object
     */
    private static InputStream getSoapInputStream(String url) {
        InputStream inputStream = null;
        try {
            URL urlObj = new URL(url);
            URLConnection urlConn = urlObj.openConnection();
            urlConn.setRequestProperty("Host", SERVICES_HOST);
            //webService
            urlConn.connect();
            inputStream = urlConn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * save the XML format string get from remote WebService as local file
     */
    private static void helloOK(Document document, String saveFileURL) {
        TransformerFactory transF = TransformerFactory.newInstance();
        try {
            Transformer transformer = transF.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "YES");
            PrintWriter pw = new PrintWriter(new FileOutputStream(saveFileURL));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            System.out.println("generate xml file succeed!");
        } catch (IllegalArgumentException | TransformerException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
