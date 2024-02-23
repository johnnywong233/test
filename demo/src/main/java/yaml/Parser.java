package yaml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
import java.util.Objects;

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
            yaml.dump(contacts, new FileWriter("contact.yaml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void load() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        File f = new File(Objects.requireNonNull(Parser.class.getClassLoader().getResource("test.yaml")).getFile());
        Object result = yaml.load(new FileInputStream(f));
        System.out.println(result.getClass());
        System.out.println(result);
    }

    @Test
    public void loadPojo() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        File f = new File(Objects.requireNonNull(Parser.class.getClassLoader().getResource("test.yaml")).getFile());
        Map<String, String> result = yaml.load(new FileInputStream(f));
        System.out.println(result);
        System.out.println(result.get("animal"));
    }

    @Test
    public void dump() {
        Yaml yaml = new Yaml();
        Pojo p = new Pojo();
        p.name = "name";
        System.out.println(yaml.dump(p));
    }


}

@NoArgsConstructor
@AllArgsConstructor
class Pojo {
    public String name;

    @Override
    public String toString() {
        return "name->" + name;
    }
}

@Data
@AllArgsConstructor
class Contact {
    private String name;
    private int age;
    private List<?> phoneNumbers;
}

@Data
@AllArgsConstructor
class Phone {
    private String name;
    private String number;
}