package utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.htmlparser.Attribute;
import org.junit.Test;

import javax.xml.soap.Detail;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnny on 2016/8/28.
 * demo of bean-util
 */
public class BeanUtilsDemo {
    //http://www.phpxs.com/code/1001489/
    public static void main(String[] args) {
        BeanUtilsDemo beanUtilsDemo = new BeanUtilsDemo();
        beanUtilsDemo.test();
    }

    @Test
    public void test() {
        try {
            Person p = new Person(new Book());
            //使用beanUtils给对象的属性赋值
            BeanUtils.setProperty(p, "username", "张三");
            //使用beanUtils获取对象的属性值
            System.out.println(BeanUtils.getProperty(p, "username"));
            //beanUtils支持属性链赋值与获得值,不过赋值前book要先实例化
            BeanUtils.setProperty(p, "book.name", "历史小说");
            System.out.println(BeanUtils.getProperty(p, "book.name"));
            System.out.println(p.getBook().getName());
            //把一个对象的值赋给另一个对象
            Person p2 = new Person();
            org.springframework.beans.BeanUtils.copyProperties(p2, p);
            System.out.println(p2.getUsername() + "," + p2.getBook().getName());
            Map<String, String> map = new HashMap<>();
            //把Map的值拷贝给一个JavaBean
            Person p3 = new Person();
            map.put("username", "张三");
            map.put("pass", "9999");
            BeanUtils.populate(p3, map);
            System.out.println(p3.getUsername() + "," + p3.getPass());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

@Data
@NoArgsConstructor
class Person {
    private String username;
    private String pass;
    private int money;
    private Book book;

    Person(Book book) {
        this.book = book;
    }
}

@Data
class Book {
    private int id;
    private String name;
    private int price;
    private String author;
    private Detail detail;
    private Attribute attribute;
}