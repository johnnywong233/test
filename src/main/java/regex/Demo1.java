package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo1 {
	public static void main(String args[]){
//		String text = "This is the text to be searched " + "for occurrences of the http:// pattern.";
//		String patStr = ".*http://.*";//匹配 http://
//		boolean matches = Pattern.matches(patStr, text);
//		System.out.println("matches = " + matches);
		
//		String text = "John writes about this, and John Doe writes about that," + 
//		" and John Wayne writes about everything.";
//		String patternString1 = "((John) (.+?)) ";//John 后跟一个单词 
//		Pattern pattern = Pattern.compile(patternString1);
//		Matcher matcher = pattern.matcher(text);
//
//		String replaceAll = matcher.replaceAll("Joe Blocks ");
//		System.out.println("replaceAll   = " + replaceAll);
//
//		String replaceFirst = matcher.replaceFirst("Joe Blocks ");
//		System.out.println("replaceFirst = " + replaceFirst);

		String text = "John writes about this, and John Doe writes about that," +
		         " and John Wayne writes about everything.";

		String patternString1 = "((John) (.+?)) ";
		Pattern pattern = Pattern.compile(patternString1);
		Matcher matcher = pattern.matcher(text);
		StringBuffer stringBuffer = new StringBuffer();

		while(matcher.find()){
		    matcher.appendReplacement(stringBuffer, "Joe Blocks ");
		    System.out.println(stringBuffer.toString());
		}
		matcher.appendTail(stringBuffer);
		System.out.println(stringBuffer.toString());

		
		
	

		
		
		
		
		
		

	}
}
