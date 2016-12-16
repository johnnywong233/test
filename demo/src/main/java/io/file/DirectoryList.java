package io.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * ������ʹ��File���list()������ʵ��Ŀ¼���б���ʾ��
 */

public class DirectoryList {
    public static void main(String[] args) {
        try {
            File path = new File(".");
            String[] myList;
            if (args.length == 0)//�����Ա���������ʾ�����ļ�
                myList = path.list();
            else				//���ù�������ʾ����ļ�
                myList = path.list(new DirectoryFilter(args[0]));
            for (int i = 0; i < myList.length; i++)
                System.out.println(myList[i]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class DirectoryFilter implements FilenameFilter {
    String myString;

    DirectoryFilter(String myString) {
        this.myString = myString;
    }

    public boolean accept(File dir, String name) {
        String f = new File(name).getName();
        return f.indexOf(myString) != -1;
    }
}
