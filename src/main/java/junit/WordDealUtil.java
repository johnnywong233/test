package junit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordDealUtil {
	
	/*
	 * https://www.ibm.com/developerworks/cn/java/j-lo-junit4/
	 * 将 Java 对象名称（每个单词的头字母大写）按照
	 * 数据库命名的习惯进行格式化
	 * 格式化后的数据为小写字母，并且使用下划线分割命名单词
	 * 
	 * 例如：employeeInfo 经过格式化之后变为 employee_info 
	 * 
	 * @param name 	 Java 对象名称
	 */ 
	
	
	public static String wordFormat4DB(String name){ 
		 Pattern p = Pattern.compile("[A-Z]"); 
		 Matcher m = p.matcher(name); 
		 StringBuffer sb = new StringBuffer(); 
		
		 while(m.find()){ 
			 m.appendReplacement(sb, "_"+m.group()); 
		 } 
		 return m.appendTail(sb).toString().toLowerCase(); 
	 }
	
	// 修改后的方法 wordFormat4DB 
	 /** 
		 * 将 Java 对象名称（每个单词的头字母大写）按照
		 * 数据库命名的习惯进行格式化
		 * 格式化后的数据为小写字母，并且使用下划线分割命名单词
		 * 如果参数 name 为 null，则返回 null 
		 * 
		 * 例如：employeeInfo 经过格式化之后变为 employee_info 
		 * 
		 * @param name Java 对象名称
		 */ 
		 public static String wordFormat4DB_1(String name){ 
			
			 if(name == null){ 
				 return null; 
			 } 
			
			 Pattern p = Pattern.compile("[A-Z]"); 
			 Matcher m = p.matcher(name); 
			 StringBuffer sb = new StringBuffer(); 
			
			 while(m.find()){ 
				 if(m.start() != 0) 
					 m.appendReplacement(sb, ("_"+m.group()).toLowerCase()); 
			 } 
			 return m.appendTail(sb).toString().toLowerCase(); 
		 }
	
	
	
	
	

}
