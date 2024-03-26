package file.xml.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

//Unmarshaller  
public class Xml2ObjectDemo {
    public static void main(String[] args) {
        try {
            File file = new File("E:\\Java_ex\\test_file\\file1.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer2.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Customer2 customer = (Customer2) jaxbUnmarshaller.unmarshal(file);
            System.out.println(customer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}