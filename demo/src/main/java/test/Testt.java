package test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Testt {
	//SplitTest
	public static void main(String args[]) throws IOException{
		
		/*
		 * java调用外部程序的方法
		 * http://gundumw100.iteye.com/blog/438696
		 */
//		Runtime.getRuntime().exec("notepad.exe"); 
		
//		Runtime runtime=Runtime.getRuntime();  
//		String[] commandArgs={"notepad.exe","c:/boot.ini"};  
//		runtime.exec(commandArgs);  

		String str = "77Hi, johnny, your life sucks..0";
		System.out.println(checkNumInString(str));
		
		
		
		
		
		
//		String str1 = "a-b";
//		String str2 = "a-b-";
//		String str3 = "-a-b";
//		String str4 = "-a-b-";
//		String str5 = "a";
//		String str6 = "-";
//		String str7 = "--";
//		String str8 = "";
//
//		split(str1);
//		split(str2);
//		split(str3);
//		split(str4);
//		split(str5);
//		split(str6);
//		split(str7);
//		split(str8);
		}
	
	public static boolean checkNumInString(String s) {
		Pattern p = Pattern.compile("\\d*");
		Matcher m = p.matcher(s);
		boolean b = m.matches();
		return b;
	}
	
	
		public static void split(String demo){
			String[] array = demo.split("-");
			int len = array.length;
			System.out.print("\"" + demo + "\" ·Ö¸îºóµÄ³¤¶ÈÎª£º" + len);
			if(len >= 0)
			{
				System.out.print(",·Ö¸îºóµÄ½á¹ûÎª£º");
				for(int i=0; i<len; i++)
			{
					System.out.print(" \""+array[i]+"\"");
			}
		}
		System.out.println();
	}
}
