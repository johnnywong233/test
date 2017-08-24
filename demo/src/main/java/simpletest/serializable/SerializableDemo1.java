package simpletest.serializable;

import com.alibaba.fastjson.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/8/24
 * Time: 23:23
 */
public class SerializableDemo1 {

    //http://www.hollischuang.com/archives/1140
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<String> stringList = new ArrayList<>();
        stringList.add("hello");
        stringList.add("world");
        stringList.add("from");
        stringList.add("johnny");
        System.out.println("init StringList" + stringList);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("stringlist"));
        objectOutputStream.writeObject(stringList);

        IOUtils.close(objectOutputStream);
        File file = new File("stringlist");
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));

        List<String> newStringList = (List<String>) objectInputStream.readObject();
        IOUtils.close(objectInputStream);
        if (file.exists()) {
            file.delete();
        }
        System.out.println("new StringList" + newStringList);
    }
}
