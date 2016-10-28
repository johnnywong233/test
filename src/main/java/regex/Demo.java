package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wajian on 2016/9/6.
 */
public class Demo {


	//todo
    public static void main(String[] args) {
        String string = "abbbaabbbaaabbb1234";
//        String string = "/m/t/wd/nl/n/p/m/wd/nl/n/p/m/wd/nl/n/p/m/v/n";
        String pattern = "";
        
        //Greedy match, max match
        Pattern p1 = Pattern.compile("\\.*bbb");
        Matcher m1 = p1.matcher(string);
        System.out.println("max match:");
        while(m1.find()) {
            System.out.println(m1.group());
            System.out.println(m1.start());
            System.out.println(m1.end());
        }

        //Reluctant match, min match
        Pattern p2 = Pattern.compile("\\.*?bbb");
        Matcher m2 = p2.matcher(string);
        System.out.println("min match:");
        while(m2.find()) {
            System.out.println(m2.group());
            System.out.println(m2.start());
            System.out.println(m2.end());
        }

        //Possessive match, total match
        Pattern p3 = Pattern.compile(".*+bbb");
        Matcher m3 = p3.matcher(string);
        System.out.println(m3.find());//output:false
    }
}