package test;

import java.util.Scanner;

public class IntegerTest {
    //
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNextInt()) {
            int ii = input.nextInt();
            System.out.println("\n === " + ii + "������ж� =====");
            Integer i = new Integer(ii);
            Integer j = new Integer(ii);
            System.out.println("new �����Ķ���" + (i == j));

            i = ii;
            j = ii;
            System.out.println("��������ת���Ķ���" + (i == j));

            i = Integer.valueOf(ii);
            j = Integer.valueOf(ii);
            System.out.println("valueof �����Ķ���" + (i == j));
        }
        input.close();
    }
}
