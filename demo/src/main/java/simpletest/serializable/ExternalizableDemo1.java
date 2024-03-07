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
 * Time: 13:26
 */
public class ExternalizableDemo1 {
    //忽略关闭流操作及删除文件操作。真正编码时千万不要忘记IOException直接抛出
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        User user = new User();
        user.setName("crazy");
        user.setAge(28);
        oos.writeObject(user);
        //Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        User newInstance = (User) ois.readObject();
        System.out.println(newInstance);
    }

    public static class User implements Externalizable {
        private String name;
        private int age;

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

        @Override
        public void writeExternal(ObjectOutput out) {
        }

        @Override
        public void readExternal(ObjectInput in) {
        }

        @Override
        public String toString() {
            return "User{name='" + name + '\'' + ", age=" + age + '}';
        }
    }
}
