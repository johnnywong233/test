package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {
	public static void main(String args[]) {
		File file = new File("C:\\work\\test\\src\\test\\resources\\LMD.txt");
		Properties prop = new Properties();
		prop.put("a", "aaa");
		prop.put("b", "bbb");
		
		//java.lang.Integer cannot be cast to java.lang.String
//		prop.put("b", new Integer(4));
		
		try {
			//can only save the second comment
			prop.store(new FileOutputStream(file), "comment for aaa");
			prop.store(new FileOutputStream(file), "comment for bbb");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File file1 = new File("C:\\work\\test\\src\\test\\resources\\already.txt");
		Properties prop1 = new Properties();
		try {
			prop1.load(new FileInputStream(file1));
			prop1.put("ADDED_ONE", "shit");
			prop1.store(new FileOutputStream(file1), "can add a new property work?");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
