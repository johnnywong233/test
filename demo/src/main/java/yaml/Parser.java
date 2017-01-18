package yaml;

import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/10/22
 * Time: 14:06
 */
public class Parser {

    @Test
    public static void test1() {
        try {
            Yaml yaml = new Yaml();
            URL url = Parser.class.getClassLoader().getResource("conf.yaml");
            if (url != null) {
                Object obj = yaml.load(new FileInputStream(url.getFile()));
                System.out.println(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void test2() {
        try {
            Contact c1 = new Contact("test1", 1, Arrays.asList(
                    new Phone("home", "1111"),
                    new Phone("work", "2222")));
            Contact c2 = new Contact("test2", 23, Arrays.asList(
                    new Phone("home", "1234"),
                    new Phone("work", "4321")));
            List<Contact> contacts = Arrays.asList(c1, c2);
            Yaml yaml = new Yaml();
            yaml.dump(contacts, new FileWriter("D:\\Java_ex\\test\\src\\test\\resources\\contact.yaml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void load() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        File f = new File("D:\\Java_ex\\test\\src\\test\\resources\\test.yaml");
        Object result = yaml.load(new FileInputStream(f));
        System.out.println(result.getClass());
        System.out.println(result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void loadPojo() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        File f = new File("D:\\Java_ex\\test\\src\\test\\resources\\test.yaml");
        Map<String, String> result = (Map<String, String>) yaml.load(new FileInputStream(f));
        System.out.println(result);
        System.out.println(result.get("animal"));
    }

    @Test
    public void dump() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Pojo p = new Pojo();
        p.name = "name";
        System.out.println(yaml.dump(p));
    }


}

class Pojo {
    public String name;

    public Pojo(String name) {
        this.name = name;
    }

    Pojo() {
    }

    @Override
    public String toString() {
        return "name->" + name;
    }
}

class Contact {
    private String name;
    private int age;
    private List<?> phoneNumbers;

    public Contact(String name, int age, List<?> phoneNumbers) {
        this.name = name;
        this.age = age;
        this.phoneNumbers = phoneNumbers;
    }

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

    public List<?> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<?> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}

class Phone {
    private String name;
    private String number;

    public Phone(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}