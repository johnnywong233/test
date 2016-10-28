package beanutil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.htmlparser.Attribute;
import org.junit.Test;

import javax.xml.soap.Detail;

/**
 * Created by johnny on 2016/8/28.
 * demo of beanutil
 */
public class BeanUtilsDemo {
    //http://www.phpxs.com/code/1001489/
    public static void main(String[] args) {
        BeanUtilsDemo beanUtilsDemo = new BeanUtilsDemo();
        beanUtilsDemo.test();
    }

    @Test
    public void test(){
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
            BeanUtils.copyProperties(p2, p);
            System.out.println(p2.getUsername()+","+p2.getBook().getName());
            Map<String,String> map = new HashMap<String, String>();
            //把Map的值拷贝给一个JavaBean
            Person p3 = new Person();
            map.put("username", "张三");
            map.put("password", "9999");
            BeanUtils.populate(p3, map );
            System.out.println(p3.getUsername()+","+p3.getPassword());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

class Person {
    private String username;
    private String password;
    private int money;
    private Book book;

    Person() {
    }

    Person(Book book) {
        this.book = book;
    }

    Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
}

class Book {

    private int id;
    private String name;
    private int price;
    private String author;
    private Detail detail;
    private Attribute attribute;

    public Attribute getAttribute() {
        return attribute;
    }
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
    public Detail getDetail() {
        return detail;
    }
    public void setDetail(Detail detail) {
        this.detail = detail;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}