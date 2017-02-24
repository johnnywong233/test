package ini4j;

import org.ini4j.Ini;

import java.io.FileInputStream;

/**
 * Author: Johnny
 * Date: 2017/2/10
 * Time: 18:52
 */
public class BeanSample {
    public static final String FILENAME = "dwarfs.ini";

    //code under this package from http://ini4j.sourceforge.net/sample/
    public static void main(String[] args) throws Exception {
        String filename = (args.length > 0) ? args[0] : FILENAME;
        Dwarfs dwarfs = new Ini(new FileInputStream(filename)).as(Dwarfs.class);
        Dwarf happy = dwarfs.getHappy();
        Dwarf doc = dwarfs.getDoc();

        System.out.println("Happy's age: " + happy.getAge());
        doc.setAge(44);
        System.out.println("Doc's age: " + doc.getAge());
    }
}
