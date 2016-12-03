package io.file;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * Created by wajian on 2016/8/22.
 */
public class ReadFileLine {
    //http://blog.csdn.net/yaowenqian99/article/details/5403694
    public static void main(String[] args) {
        int lineNumber = 2;
        File sourceFile = new File("C:\\work\\test\\src\\test\\resources\\test.txt");

        try {
            readAppointedLineNumber(sourceFile, lineNumber);
            System.out.println(getTotalLines(sourceFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //read the desired line of file
    static String readAppointedLineNumber(File sourceFile, int lineNumber)
            throws IOException {
        FileReader in = new FileReader(sourceFile);
        LineNumberReader reader = new LineNumberReader(in);
        String s = "";
        if (lineNumber <= 0 || lineNumber > getTotalLines(sourceFile)) {
            System.out.println("out of range of the line number of this file!");
            System.exit(0);
        }
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
            if ((lines - lineNumber) == 0) {
                System.out.println(s);
                System.exit(0);
            }
        }
        reader.close();
        in.close();
        return s;
    }

    //get the total lines of file
    static int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
            if (lines >= 2) {
                if (s != null) {
                    System.out.println(s + "$");
                }
            }
        }
        reader.close();
        in.close();
        return lines;
    }
}
