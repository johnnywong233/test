package file.xml;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XML2VCF {
    public static void main(String[] args) throws IOException, DocumentException {
        // use dom4j to read Contact.xml
        SAXReader reader = new SAXReader();
        Document document = reader.read(new ClassPathResource("Contact.xml").getFile());
        Element root = document.getRootElement();
        // contact List
        List<?> contacts = root.elements("Contact");

        StringBuilder sb = new StringBuilder();

        // append into StringBuilder
        for (Object contactObj : contacts) {
            Element eleContact = (Element) contactObj;

            String name = eleContact.elementText("DisplayName");
            String number = eleContact.element("PhoneElement").attribute("Value").getValue();

            sb.append("BEGIN:VCARD\nVERSION:2.1\n");
            sb.append("FN:");
            sb.append(name);
            sb.append("\nTEL;CELL:");
            sb.append(number);
            sb.append("\nEND:VCARD\n");
        }

        // StringBuilder write into vcf file
        try {
            FileUtils.writeStringToFile(new File("test.vcf"), sb.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
