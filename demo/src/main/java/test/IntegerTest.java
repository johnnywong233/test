package test;

import java.util.Scanner;

public class IntegerTest {
    //
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNextInt()) {
            int ii = input.nextInt();
            System.out.println("\n === " + ii + "=====");
            Integer i = new Integer(ii);
            Integer j = new Integer(ii);
            System.out.println("new: " + (i == j));

            i = ii;
            j = ii;
            System.out.println("直接赋值" + (i == j));

            i = Integer.valueOf(ii);
            j = Integer.valueOf(ii);
            System.out.println("valueOf: " + (i == j));
        }
        input.close();
    }
}
