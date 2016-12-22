package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wajian on 2016/9/6.
 * test of regex
 */
public class RegexDemo {
    public static void main(String[] args) {
        sillyTest();
        threeMatches();

        String str = "77Hi, johnny, your life sucks..0";
        System.out.println(checkNumInString(str));

    }

    private static boolean checkNumInString(String s) {
        Pattern p = Pattern.compile("\\d*");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    private static void threeMatches(){
        String string = "abbbaabbbaaabbb1234";
//        String string = "/m/t/wd/nl/n/p/m/wd/nl/n/p/m/wd/nl/n/p/m/v/n";
        //TODO: difference
        //Greedy match, max match
        Pattern p1 = Pattern.compile("\\.*bbb");
        Matcher m1 = p1.matcher(string);
        System.out.println("max match:");
        while (m1.find()) {
            System.out.println(m1.group());
            System.out.println(m1.start());
            System.out.println(m1.end());
        }

        //Reluctant match, min match
        Pattern p2 = Pattern.compile("\\.*?bbb");
        Matcher m2 = p2.matcher(string);
        System.out.println("min match:");
        while (m2.find()) {
            System.out.println(m2.group());
            System.out.println(m2.start());
            System.out.println(m2.end());
        }

        //Possessive match, total match
        Pattern p3 = Pattern.compile(".*+bbb");
        Matcher m3 = p3.matcher(string);
        System.out.println(m3.find());//output:false
    }


    private static void sillyTest(){
        String text = "This is the text to be searched " + "for occurrences of the http:// pattern.";
        String patStr = ".*http://.*";//匹配 http://
        boolean matches = Pattern.matches(patStr, text);
        System.out.println("matches = " + matches);

        String text1 = "John writes about this, and John Doe writes about that," +
                " and John Wayne writes about everything.";
        String patternString1 = "((John) (.+?)) ";//John 后跟一个单词
        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(text1);
        String replaceAll = matcher.replaceAll("Joe Blocks ");
        System.out.println("replaceAll   = " + replaceAll);

        String replaceFirst = matcher.replaceFirst("Joe Blocks ");
        System.out.println("replaceFirst = " + replaceFirst);

        String text2 = "John writes about this, and John Doe writes about that," +
                " and John Wayne writes about everything.";
        String patternString2 = "((John) (.+?)) ";
        Pattern pattern2 = Pattern.compile(patternString2);
        Matcher matcher2 = pattern2.matcher(text2);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher2.find()) {
            matcher2.appendReplacement(stringBuffer, "Joe Blocks ");
            System.out.println(stringBuffer.toString());
        }
        matcher2.appendTail(stringBuffer);
        System.out.println(stringBuffer.toString());
    }
}