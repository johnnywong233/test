package test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Scanner;

public class TestScanner {
    public static void main(String args[]) {
        try {
            //TODO
			Scanner in = new Scanner(Paths.get(URI.create("D:\\Java_ex\\test\\src\\test\\resources\\johnny.txt")));
//            Scanner in = new Scanner(Paths.get("johnny.txt"));
            System.out.println(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
