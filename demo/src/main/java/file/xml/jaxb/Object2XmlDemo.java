package file.xml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

//Marshaller  
public class Object2XmlDemo {
    public static void main(String[] args) {

        Customer2 customer = new Customer2();
        customer.setId(100);
        customer.setName("suo");
        customer.setAge(29);

        try {
            File file = new File("E:\\Java_ex\\test_file\\file1.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer2.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed  
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(customer, file);
            jaxbMarshaller.marshal(customer, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
