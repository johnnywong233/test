package simpletest.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Author: Johnny
 * Date: 2017/8/20
 * Time: 19:57
 * 单例模式若implements Serializable，则不是单例，即被破坏
 */
@SuppressWarnings("resource")
public class SerializableDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Write Obj to file
        
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        oos.writeObject(Singleton.getSingleton());
        //Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Singleton newInstance = (Singleton) ois.readObject();
        //判断是否是同一个对象
        System.out.println(newInstance == Singleton.getSingleton());
    }

    /**
     * double-check synchronized to write a Singleton
     */
    static class Singleton implements Serializable {
        private static final long serialVersionUID = 6779035480090482398L;
        private volatile static Singleton singleton;

        private Singleton() {
        }

        /**
         *  add this to avoid Singleton being disrupted by Serializable
         */
        private Object readResolve() {
            return singleton;
        }

        static Singleton getSingleton() {
            if (singleton == null) {
                synchronized (Singleton.class) {
                    if (singleton == null) {
                        singleton = new Singleton();
                    }
                }
            }
            return singleton;
        }
    }
}
