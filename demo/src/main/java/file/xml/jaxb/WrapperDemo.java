package file.xml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.io.File;

/**
 * Created by johnny on 2016/8/28.
 */
public class WrapperDemo {

    public static void main(String[] args) {

        MultiCoder coder = new MultiCoder();
        coder.setId(111);
        coder.setName("Johnny");
        coder.setAge(26);
        coder.setLanguage(new String[]{"Java", "Python", "SQL"});

        jaxbMarshal(coder);

    }

    //JAXB marshal: convert object into XML file
    private static void jaxbMarshal(MultiCoder coder) {
        try {
            File file = new File("C:\\work\\Demo\\johnny\\src\\test\\resources\\testJaxbWrapper.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(MultiCoder.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //write into file and print onto console
            jaxbMarshaller.marshal(coder, file);
            jaxbMarshaller.marshal(coder, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}

@XmlRootElement
@XmlType(propOrder = {"name", "age", "language"})
class MultiCoder {

    private String name;
    private int age;
    private int id;
    private String[] language;

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @XmlElement
    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Coder [id=" + id + ",name=" + name + ",age=" + age + "]";
    }

    //use @XmlElementWrapper annotation will create another xml layer
    @XmlElementWrapper(name = "languages")
    @XmlElement(name = "language")
    public String[] getLanguage() {
        return language;
    }

    public void setLanguage(String[] language) {
        this.language = language;
    }
}