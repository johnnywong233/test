package io.file;

import java.io.File;
import java.util.Scanner;

/**
 * Created by johnny on 2016/8/17.
 */

public class FileNameFilterDemo {

    //http://www.jb51.net/article/52254.htm
    public static void main(String[] args) {
        FileNameFilter fnf = new FileNameFilter();
        Scanner kb = new Scanner(System.in);
        String str1;
        String str2;
        System.out.print("input file directory:");
        str1 = kb.next();
        System.out.print("input filter filename extension:");
        str2 = kb.next();
        fnf.filter(str1, str2);
        kb.close();
    }
}

class FileNameFilter {
    public void filter(String strPath, String name) {
        File f = new File(strPath);
        if (f.isDirectory()) {
            File[] fList = f.listFiles();
			assert fList != null;
			for (File aFList : fList) {
                if (aFList.isFile() && aFList.getName().endsWith(name)) {
                    System.out.println(aFList.getName());
                }
            }
        }
    }
}