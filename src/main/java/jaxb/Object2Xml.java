package jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

//Marshaller
public class Object2Xml {
    public static void main(String[] args) {  
    	  
        Customer1 customer = new Customer1();  
        customer.setId(100);  
        customer.setNames(new String[]{"name-a","name-b","name-c"});  
        customer.setAge(29);  
        try {  
            File file = new File("E:\\Java_ex\\test_file\\file1.xml");  
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer1.class);  
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
