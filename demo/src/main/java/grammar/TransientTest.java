package grammar;

import lombok.Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TransientTest {
    public static void main(String[] args) {

        User user = new User();
        user.setUsername("Alexia");
        user.setPassword("123456");

        System.out.println("read before Serializable: ");
        System.out.println("username: " + user.getUsername());
        System.err.println("password: " + user.getPassword());

        try {
            ObjectOutputStream os = new ObjectOutputStream(
                    new FileOutputStream("user.txt"));
            os.writeObject(user);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("user.txt"));
            user = (User) is.readObject();
            is.close();
            System.out.println("\nread after Serializable: ");
            System.out.println("username: " + user.getUsername());
            System.err.println("password: " + user.getPassword());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

@Data
class User implements Serializable {
    private static final long serialVersionUID = 8294180014912103005L;
    private String username;
    private transient String password;
}