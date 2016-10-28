package test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Scanner;

public class Testeee {
	public static void main(String args[]) {
		try {
//			Scanner in = new Scanner(Paths.get(URI.create("C:\\work\\test\\src\\test\\resources\\myfile.txt")));
			Scanner in = new Scanner(Paths.get("myfile.txt"));
			System.out.println(in);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
}
