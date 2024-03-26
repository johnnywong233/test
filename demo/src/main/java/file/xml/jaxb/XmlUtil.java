package file.xml.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by johnny on 2016/8/21.
 * demo of jaxb annotation for inner wrap
 */
public class XmlUtil {
    //http://www.jb51.net/article/48966.htm
    public static void main(String[] args) {
        NewClassB classB = new NewClassB();
        classB.setClassBId(22);
        classB.setClassBName("B2");
        NewClassA classA = new NewClassA();
        classA.setClassAId(11);
        classA.setClassAName("A1");
        classA.setClassB(classB);
        System.out.println(XmlUtil.toXML(classA));
    }

    private static String toXML(Object obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromXML(String xml, Class<T> valueType) {
        try {
            JAXBContext context = JAXBContext.newInstance(valueType);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

@XmlRootElement(namespace = "jaxb.demo1")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
class NewClassA {
    private int classAId;

    @XmlElement(name = "ClassAName")
    private String classAName;
    private NewClassB classB;
}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
class NewClassB {
    private int classBId;
    private String classBName;
}