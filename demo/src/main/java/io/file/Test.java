package io.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test  {
	/*
	 * �����֪����261��Java��������.04.08
	 */
	public static void main(String[] args) {
		
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			fis = new FileInputStream("C:\\Users\\wajian\\Documents\\Test\\user.txt");
			isr = new InputStreamReader(fis,"GBK");
			br = new BufferedReader(isr);
			String s = br.readLine();
			System.out.println(s);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			System.out.println(fis.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

