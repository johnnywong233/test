package simpletest.serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableTest implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int staticVar = 5;

    @SuppressWarnings("static-access")
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

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
