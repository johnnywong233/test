package file.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static utils.FileUtil.readFile;

public class ParseWrongXml {
    public static void main(String[] args) throws DocumentException, IOException {
        String xml = "ï»¿<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
                "<error xmlns=\"https://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" +
                "  <code>AccountIsDisabled</code>\n" +
                "  <message xml:lang=\"en-US\">The specified account is disabled.\n" +
                "RequestId:eea42317-0002-001b-23d5-6ff5e7000000\n" +
                "Time:2017-01-16T08:46:24.7954385Z</message>\n" +
                "</error>\n";
        xml = xml.replaceAll("[^\\x20-\\x7e]", "");
        Document document = DocumentHelper.parseText(xml);
        System.out.println(document.getXMLEncoding() + document.getRootElement().getText());

        String filePath = new ClassPathResource("wrong.xml").getFile().getPath();
        document = DocumentHelper.parseText(readFile(filePath));
        System.out.println(document);
    }

}
