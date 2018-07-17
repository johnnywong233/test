package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by wajian on 2016/8/13.
 */
public class SerializeDemo {
    //http://www.runoob.com/java/java-serialization.html
    public static void main(String[] args) {
        Employee e = new Employee();
        e.name = "Reyan Ali";
        e.address = "Phokka Kuan, Ambehta Peer";
        e.ssn = 11122333;
        e.number = 101;
        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\work\\test\\src\\main\\resources\\employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(e);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /employee.ser\n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
