package simpletest.serializable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableTest implements Serializable {
    private static final long serialVersionUID = 1L;

    public static int staticVar = 5;

    public static void main(String[] args) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("result.obj"));
            out.writeObject(new SerializableTest());
            out.close();

            //modify to 10 after serialize
            SerializableTest.staticVar = 10;

            ObjectInputStream oin = new ObjectInputStream(new FileInputStream("result.obj"));
            SerializableTest t = (SerializableTest) oin.readObject();
            oin.close();

            System.out.println(t.staticVar);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
