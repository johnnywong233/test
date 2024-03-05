package io.file;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by johnny on 2016/8/22.
 * DataInputStream的好处在于在从文件读出数据时，不用费心地自行判断读入字符串时或读入int类型时何时将停止，
 * 使用对应的readUTF()和readInt()方法就可以正确地读入完整的类型数据。
 */
public class DataStreamDemo {
    //http://davidisok.iteye.com/blog/2106489
    public static void main(String[] args) {
        Member[] members = {new Member("Justin", 90),
                new Member("momor", 95),
                new Member("Bush", 88)};
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("test.txt"));

            for (Member member : members) {
                dataOutputStream.writeUTF(member.getName());
                dataOutputStream.writeInt(member.getAge());
            }

            //all data flush into the file
            dataOutputStream.flush();
            dataOutputStream.close();

            DataInputStream dataInputStream = new DataInputStream(new FileInputStream("test.txt"));

            //read data as object
            for (int i = 0; i < members.length; i++) {
                String name = dataInputStream.readUTF();
                int score = dataInputStream.readInt();
                members[i] = new Member(name, score);
            }
            dataInputStream.close();

            for (Member member : members) {
                System.out.printf("%s\t%d%n", member.getName(), member.getAge());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

@Data
@AllArgsConstructor
class Member {
    private String name;
    private int age;
}
