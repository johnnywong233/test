package ini4j;

import org.ini4j.Ini;

import java.io.FileInputStream;

/**
 * Author: Johnny
 * Date: 2017/2/10
 * Time: 18:53
 */
public class BeanEventSample {

    public static void main(String[] args) throws Exception {
        String filename = (args.length > 0) ? args[0] : BeanSample.FILENAME;
        Ini ini = new Ini(new FileInputStream(filename));
        Dwarf sneezy = ini.get("sneezy").as(Dwarf.class);

        sneezy.addPropertyChangeListener("age", event -> {
            System.out.println("property " + event.getPropertyName() + " change");
            System.out.println(event.getOldValue() + " -> " + event.getNewValue());
        });
        System.out.println("Sneezy's age: " + sneezy.getAge());
        sneezy.setAge(44);
        System.out.println("Sneezy's age: " + sneezy.getAge());
    }
}
