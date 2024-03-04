package file.xml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.File;

/**
 * Created by johnny on 2016/8/28.
 */
public class JAXBDemo {
	
    public static void main(String[] args) {

    	Coder coder = new Coder();
    	coder.setId(111);
    	coder.setName("Johnny");
    	coder.setAge(26);

        jaxbMarshal(coder);

        File file = new File("C:\\work\\Demo\\johnny\\src\\test\\resources\\testJAXB.xml");
        jaxbUnmarshal(file);

    }

    //JAXB unmarshal: convert XML file into file object
    private static void jaxbUnmarshal(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Coder.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Coder notLikeACoder = (Coder) jaxbUnmarshaller.unmarshal(file);
            System.out.println(notLikeACoder.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    //JAXB marshal: convert object into XML file
    private static void jaxbMarshal(Coder coder) {
		try {
            File file = new File("C:\\work\\Demo\\johnny\\src\\test\\resources\\testJAXB.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Coder.class);
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
class Coder {

	private String name;
	private int age;
	private int id;

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

//    private List<Coder> coders;
//
//    public void setCoders(List<Coder> coders) {
//        this.coders = coders;
//    }
//
//    public List<Coder> getCoders(){
//        return coders;
//    }
//
//
//    @Override
//    public String toString()
//    {
//        StringBuffer str = new StringBuffer();
//        for( Coder coder : this.coders )
//        {
//            str.append( coder.toString() );
//            str.append("\n");
//        }
//        return str.toString();
//    }


}