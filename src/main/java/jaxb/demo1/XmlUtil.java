package jaxb.demo1;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by johnny on 2016/8/21.
 */
public class XmlUtil {
    //http://www.jb51.net/article/48966.htm
    public static void main(String[] args) {
        ClassB classB = new ClassB();
        classB.setClassBId(22);
        classB.setClassBName("B2");
        ClassA classA = new ClassA();
        classA.setClassAId(11);
        classA.setClassAName("A1");
        classA.setClassB(classB);
        System.out.println(XmlUtil.toXML(classA));
    }

    public static String toXML(Object obj) {
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

@XmlRootElement(namespace="jaxb.demo1")
class ClassA {
    private int classAId;
    private String classAName;
    private ClassB classB;
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
    public ClassB getClassB() {
        return classB;
    }
    public void setClassB(ClassB classB) {
        this.classB = classB;
    }
}

//
class ClassB {
    private int classBId;
    private String classBName;
    public int getClassBId() {
        return classBId;
    }
    public void setClassBId(int classBId) {
        this.classBId = classBId;
    }
    public String getClassBName() {
        return classBName;
    }
    public void setClassBName(String classBName) {
        this.classBName = classBName;
    }
}
