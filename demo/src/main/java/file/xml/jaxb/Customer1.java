package file.xml.jaxb;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer1 {
    String[] names; //diff
    int age;
    int id;

    //使用@XmlElementWrapper注解后，将会在原xml结点上再包装一层xml  
    @XmlElementWrapper(name = "allnames")
    @XmlElement(name = "myname")
    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    @XmlElement
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ",names=" + names + ",age=" + age + "]";
    }

}
