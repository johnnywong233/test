package file.xml.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by johnny on 2016/9/7.
 * demo of jaxb
 */
public class AdaptersDemo extends XmlAdapter<String, LocalDate> {

    public static void main(String[] args) {
        AdaptersDemo demo = new AdaptersDemo();
        Person person = new Person();
        String date = "2016-09-07";
        LocalDate localDate = null;
        try {
            localDate = demo.unmarshal(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        person.setBirthday(localDate);

        jaxbMarshal(person);

        //TODO: why error?
        File file = new File("C:\\work\\Demo\\johnny\\src\\test\\resources\\testJaxbAdapter.xml");
        jaxbUnmarshal(file);

    }

    //JAXB unmarshal: convert XML file into file object
    private static void jaxbUnmarshal(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Person person = (Person) jaxbUnmarshaller.unmarshal(file);
            System.out.println(person.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    //JAXB marshal: convert object into XML file
    private static void jaxbMarshal(Person person) {
        try {
            File file = new File("C:\\work\\Demo\\johnny\\src\\test\\resources\\testJaxbAdapter.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //write into file and print onto console
            jaxbMarshaller.marshal(person, file);
            jaxbMarshaller.marshal(person, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    //unmarshal basic data type to complex data type
    @Override
    public LocalDate unmarshal(String date) {
        return LocalDate.parse(date);
    }

    //marshal basic data type to complex data type
    @Override
    public String marshal(LocalDate date) {
        return date.toString();
    }


}

@Data
@XmlRootElement(name = "Persons")
class Person {
    private List<Person> person;
    private LocalDate birthday;

    @XmlJavaTypeAdapter(AdaptersDemo.class)
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Person person : this.person) {
            str.append(person.toString());
            str.append("\n");
        }
        return str.toString();
    }
}
