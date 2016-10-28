package xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by wajian on 2016/8/28.
 */
public class GenericDemo {
    //TODO:http://suo.iteye.com/blog/1233603
}

/*
当java对象的某个属性使用泛型时，普通对象都没问题，但是遇到HashSet这种集合类封装的元素时，就会出现元素内容序列化不出来的问题
 */

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.PROPERTY)
class Customer<T> {
    private String name;
    private int age;
    private int id;
    T t;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Customer [id=" + id + ",name=" + name + ",age=" + age + ",t=" + t + "]";
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}

//@XmlRootElement
@XmlAccessorType(value = XmlAccessType.PROPERTY)
class NoGenericCustomer {
    private String name;
    private int age;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Customer [id=" + id + ",name=" + name + ",age=" + age + "]";
    }
}