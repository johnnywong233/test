package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by johnny on 2016/8/17.
 */
public class RegexUtils {
    /**
     * verify Email
     *
     * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return verify success return true, fail return false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * verify ID number
     *
     * @param idCard can be 15 or 18 digits, and the last number can be X
     * @return verify success return true, fail return false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z\\d]";
        return Pattern.matches(regex, idCard);
    }
    /**
     * verify固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return verify success return true, fail return false
     */
    public static boolean checkPhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * verify整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return verify success return true, fail return false
     */
    public static boolean checkDigit(String digit) {
        String regex = "-?[1-9]\\d+";
        return Pattern.matches(regex, digit);
    }

    /**
     * verify整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return verify success return true, fail return false
     */
    public static boolean checkDecimals(String decimals) {
        String regex = "-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex, decimals);
    }

    /**
     * verify blank space character
     *
     * @param blankSpace: 空格、\t、\n、\r、\f、\x0B
     * @return verify success return true, fail return false
     */
    public static boolean checkBlankSpace(String blankSpace) {
        String regex = "\\s+";
        return Pattern.matches(regex, blankSpace);
    }

    /**
     * verify Chinese character
     *
     * @param chinese 中文字符
     * @return verify success return true, fail return false
     */
    public static boolean checkChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex, chinese);
    }

    /**
     * verify date(year, month, day)
     *
     * @param birthday date. format: 1992-09-03, or 1992.09.03
     * @return verify success return true, fail return false
     */
    public static boolean checkBirthday(String birthday) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex, birthday);
    }

    /**
     * verify URL address
     *
     * @return verify success return true, fail return false
     */
    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * 获取网址 URL 的一级域名
     */
    public static String getDomain(String url) {
        Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        //get full domain name
        // Pattern p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        matcher.find();
        return matcher.group();
    }

    /**
     * match china postcode
     *
     * @param postcode 邮政编码
     * @return verify success return true, fail return false
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * match IP address(simple match.格式，如：192.168.1.1，127.0.0.1，没有matchIP段的大小)
     *
     * @param ipAddress IPv4 standard address
     * @return verify success return true, fail return false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }
}
