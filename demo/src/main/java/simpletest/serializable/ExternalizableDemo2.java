package simpletest.serializable;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Author: Johnny
 * Date: 2017/8/20
 * Time: 13:31
 */
public class ExternalizableDemo2 {
    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        User user = new User();
        user.setName("johnny");
        user.setAge(27);
        oos.writeObject(user);
        File file = new File("tempFile");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        User newInstance = (User) ois.readObject();
        System.out.println(newInstance);
    }

    public static class User implements Externalizable {
        private String name;
        private int age;

        //must add this
        public User( ) {
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

        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(name);
            out.writeInt(age);
        }

        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            name = (String) in.readObject();
            age = in.readInt();
        }

        @Override
        public String toString() {
            return "User{name='" + name + '\'' + ", age=" + age + '}';
        }
    }
}
