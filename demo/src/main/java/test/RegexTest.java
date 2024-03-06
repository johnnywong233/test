package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Johnny
 * Date: 2018/4/28
 * Time: 3:12
 */
public class RegexTest {
    public static void main(String[] args) {

        final String regex = "([=+]|\\s|\\p{P}|[A-Za-z\\d]|[\u4E00-\u9FA5])+";
        StringBuilder line = new StringBuilder();
        System.out.println("++++++++++++++++++++++++++++++");
        for (int i = 0; i < 10; i++) {
            line.append(
                    "http://hh.ooxx.com/ershoufang/?PGTID=14366988648680=+.7342327926307917&ClickID=1&key=%2525u7261%2525u4E39%2525u5BCC%2525u8D35%2525u82B1%2525u56ED&sourcetype=1_5");
            line.append(
                    "http://wiki.corp.com/index.php?title=Track%E6%A0%87%E5%87%86%E6%97%A5%E5%BF%97Hive%E8%A1%A8-%E5%8D%B3%E6%B8%85%E6%B4%97%E5%90%8E%E7%9A%84%E6%97%A5%E5%BF%97");
            line.append(
                    "http://www.baidu.com/s?ie=UTF-8&wd=58%cd%ac%b3%c7%b6%fe%ca%d6%b3%b5%b2%e2%ca%d4%ca%fd%be%dd&tn=11000003_hao_dg");
            line.append("http://cs.ooxx.com/yewu/?key=城&cmcskey=的设计费开始低&final=1&jump=1&specialtype=gls");
            line.append(
                    "http%3A%2F%2Fcq.ooxx.com%2Fjob%2F%3Fkey%3D%25E7%25BD%2591%25E4%25B8%258A%25E5%2585%25BC%25E8%2581%258C%26cmcskey%3D%25E7%25BD%2591%25E4%25B8%258A%25E5%2585%25BC%25E8%2581%258C%26final%3D1%26jump%3D2%26specialtype%3Dgls%26canclequery%3Disbiz%253D0%26sourcetype%3D4");
        }
        line.append(" \001 11111111111111111111111111");
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                System.out.println(matcher.group());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("line size: " + line.length());
    }
}
