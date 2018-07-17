package file.xml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.File;
import java.util.List;
import java.time.LocalDate;

/**
 * Created by wajian on 2016/9/7.
 * demo of jaxb
 */
public class AdaptersDemo extends XmlAdapter<String, LocalDate> {

    //unmarshal basic data type to complex data type
    @Override
    public LocalDate unmarshal(String date) throws Exception {
        return LocalDate.parse(date);
    }

    //marshal basic data type to complex data type
    @Override
    public String marshal(LocalDate date) throws Exception {
        return date.toString();
    }

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


}

@XmlRootElement(name = "Persons")
class Person {
    private List<Person> person;
    private LocalDate birthday;

    public List<Person> getPersons() {
        return person;
    }

    public void setPersons(List<Person> person) {
        this.person = person;
    }

    @XmlJavaTypeAdapter(AdaptersDemo.class)
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return birthday;
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
