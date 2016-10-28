package io.file;

import java.io.File;
import java.util.Scanner;

/**
 * Created by wajian on 2016/8/17.
 */

public class FileNameFilterDemo{
	
	//http://www.jb51.net/article/52254.htm
    public static void main(String[] args){
        FileNameFilter fnf = new FileNameFilter();
        Scanner kb = new Scanner(System.in);
        String str1 = new String();
        String str2 = new String();
        System.out.print("input file diectory:");
        str1 = kb.next();
        System.out.print("input filter filename extension:");
        str2 = kb.next();
        fnf.filter(str1, str2);
        if (kb != null)
        	kb.close();
    }
}

class FileNameFilter {
	public void filter(String strPath, String fname){
		File f = new File(strPath);
		if(f.isDirectory()){
			File[] fList  = f.listFiles();
			for(int i = 0; i < fList.length; i++){
				if(fList[i].isFile() && fList[i].getName().endsWith(fname)){
					System.out.println(fList[i].getName());
				}
			}
		}
		
	}
}