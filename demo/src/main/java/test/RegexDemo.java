package test;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
        System.out.println("number in this string? " + checkNumInString(str));

        //TODO: usage
        String ip = "10.46.128.218";
        System.out.println(ip2int(ip));

        System.out.println(int2iP(170819802L));

    }

    private static boolean checkNumInString(String s) {
        Pattern p = Pattern.compile("\\d.*");//care for this
        Matcher m = p.matcher(s);
        return m.matches();
    }

    private static void threeMatches() {
        String string = "abbbaabbbaaabbb1234";
//        String string = "/m/t/wd/nl/n/p/m/wd/nl/n/p/m/wd/nl/n/p/m/v/n";
        //Greedy match, max match
        Pattern p1 = Pattern.compile("bbb.*");
        Matcher m1 = p1.matcher(string);
        System.out.println("max match:");
        while (m1.find()) {
            System.out.println(m1.group());
            System.out.println(m1.start());
            System.out.println(m1.end());
        }

        //Reluctant match, min match
        Pattern p2 = Pattern.compile("bbb.*?");
        Matcher m2 = p2.matcher(string);
        System.out.println("min match:");//find 3 group string match "bbb"
        while (m2.find()) {
            System.out.println(m2.group());
            System.out.println(m2.start());
            System.out.println(m2.end());
        }

        //Possessive match, total match
        Pattern p3 = Pattern.compile(".*+bbb");
        Matcher m3 = p3.matcher(string);
        System.out.println("Possessive match: " + m3.find());//output:false
    }

    /**
     * convert string ip into long
     */
    public static Long ip2int(String ip) {
        Long num = 0L;
        if (ip == null) {
            return num;
        }
        try {
            ip = ip.replaceAll("[^0-9\\.]", ""); //去除字符串前的空字符
            String[] ips = ip.split("\\.");
            if (ips.length == 4) {
                num = Long.parseLong(ips[0], 10) * 256L * 256L * 256L + Long.parseLong(ips[1], 10) * 256L * 256L + Long.parseLong(ips[2], 10) * 256L + Long.parseLong(ips[3], 10);
                //j>>>i == j/(int)(Math.pow(2,i)), where i and j are int
                num = num >>> 0;
            }
        } catch (NullPointerException ex) {
            System.out.println(ip);
        }
        return num;
    }

    /**
     * parse long into string ip
     * http://www.bianceng.cn/Programming/Java/index3.htm
     */
    public static String int2iP(Long num) {
        String str;
        Long[] tt = new Long[4];
        tt[0] = (num >>> 24);
        tt[1] = ((num << 8) >>> 24);
        tt[2] = (num << 16) >>> 24;
        tt[3] = (num << 24) >>> 24;
        str = (tt[0]) + "." + (tt[1]) + "." + (tt[2]) + "." + (tt[3]);
        return str;
    }

    /**
     * 将整数表示的ip地址转换为字符串表示.
     *
     * @param ip 32位整数表示的ip地址
     * @return 点分式表示的ip地址
     */
    public static String long2Ip(final long ip) {
        final long[] mask = {0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000};
        final StringBuilder ipAddress = new StringBuilder();
        for (int i = 0; i < mask.length; i++) {
            ipAddress.insert(0, (ip & mask[i]) >> (i * 8));
            if (i < mask.length - 1) {
                ipAddress.insert(0, ".");
            }
        }
        return ipAddress.toString();
    }

    /**
     * 获取访问用户的客户端IP（适用于公网与局域网）.
     */
    public static String getIpAddr(final HttpServletRequest request)
            throws Exception {
        if (request == null) {
            throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
        }
        String ipString = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipString.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipString = str;
                break;
            }
        }
        return ipString;
    }


    private static void sillyTest() {
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