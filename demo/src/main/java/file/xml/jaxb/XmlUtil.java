package file.xml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
class NewClassA {
    private int classAId;

    @XmlElement(name = "ClassAName")
    private String classAName;
    private NewClassB classB;

    public int getClassAId() {
        return classAId;
    }

    public void setClassAId(int classAId) {
        this.classAId = classAId;
    }

    public String getClassAName() {
        return classAName;
    }

    public void setClassAName(String classAName) {
        this.classAName = classAName;
    }

    public NewClassB getClassB() {
        return classB;
    }

    public void setClassB(NewClassB classB) {
        this.classB = classB;
    }
}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class NewClassB {
    private int ClassBId;
    private String ClassBName;

    public int getClassBId() {
        return ClassBId;
    }

    public void setClassBId(int classBId) {
        this.ClassBId = classBId;
    }

    public String getClassBName() {
        return ClassBName;
    }

    public void setClassBName(String classBName) {
        this.ClassBName = classBName;
    }
}