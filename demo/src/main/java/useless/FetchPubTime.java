package useless;

import utils.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchPubTime {

    private static Pattern pDetail = Pattern.compile("(20\\d{2}[-/]\\d{1,2}[-/]\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})|(20\\d{2}年\\d{1,2}月\\d{1,2}日)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    //如果是仅仅抽取年月日，则按照上面的，如果是抽取年月日-时分秒，则按照下面的
    private static Pattern p = Pattern.compile("(20\\d{2}[-/]\\d{1,2}[-/]\\d{1,2})|(20\\d{2}年\\d{1,2}月\\d{1,2}日)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);


    /*http://www.phpxs.com/code/1009947/
     * 对网页中各种不同格式的发布时间进行抽取，将发布时间以规整的“yyyy-MM-dd HH:mm:ss”格式表示出来，
     * 只能尽量追求精确，但是因为网络发布时间的格式十分灵活，所以做不到百分百地正确抽取
     */
    public static void main(String[] args) {

    }

    /**
     * 表示url中连续的8位日期，例如http://www.baidu.com/20140311/2356.html
     */
    private static String url_reg_whole = "([-|/|_]{1}20\\d{6})";
    /**
     * 表示 用-或者/隔开的日期,有年月日的，例如 http://www.baidu.com/2014-3-11/2356.html
     */
    private static String url_reg_sep_ymd = "([-|/|_]{1}20\\d{2}[-|/|_]{1}\\d{1,2}[-|/|_]{1}\\d{1,2})";
    /**
     * 表示 用-或者/隔开的日期,只有年和月份的，例如 http://www.baidu.com/2014-3/2356.html
     */
    private static String url_reg_sep_ym = "([-|/|_]{1}20\\d{2}[-|/|_]{1}\\d{1,2})";
    private static Calendar current = Calendar.getInstance();
    /**
     * 格式正确的时间正则表达式
     */
    private static String rightTimeReg = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";

    /**
     * @param url url
     * @param urlContent content
     * @return time string
     */
    public static String getPubTimeVarious(String url, String urlContent) {

        String pubTime = getPubTimeFromUrl(url);

        //链接里面没有，匹配文本中的
        if (pubTime == null) {
            if (urlContent != null && !"".equals(urlContent.trim())) {
                return extractPageDate(urlContent);
            }
        }

        return pubTime;
    }

    /**
     * 从url里面抽取出发布时间，返回YYYY-MM-DD HH:mm:ss格式的字符串
     *
     * @param url url
     * @return time string
     */
    public static String getPubTimeFromUrl(String url) {
        Pattern whole = Pattern.compile(url_reg_whole);
        Matcher matcher = whole.matcher(url);
        if (matcher.find(0) && matcher.groupCount() > 0) {
            String time = matcher.group(0);
            time = time.substring(1, time.length());
            //每一步都不能够超出当前时间
            if (current.compareTo(TimeUtils.strToCalendar(time, "yyyyMMdd")) >= 0) {

                return time.substring(0, 4) + "-" + time.substring(4, 6) + "-" +
                        time.substring(6, 8) + " " + "00:00:00";
            }
        }

        Pattern pSep = Pattern.compile(url_reg_sep_ymd);
        Matcher mSep = pSep.matcher(url);
        if (mSep.find(0) && mSep.groupCount() > 0) {
            String time = mSep.group(0);
            time = time.substring(1, time.length());
            String[] seg = time.split("[-|/|_]{1}");
            Calendar theTime = Calendar.getInstance();
            theTime.set(Calendar.YEAR, Integer.parseInt(seg[0]));
            theTime.set(Calendar.MONTH, Integer.parseInt(seg[1]));
            theTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(seg[2]));
            if (current.compareTo(theTime) >= 0) {
                return seg[0] + "-" + seg[1] + "-" + seg[2] + " " + "00:00:00";
            }
        }

        Pattern pattern = Pattern.compile(url_reg_sep_ym);
        Matcher matcher1 = pattern.matcher(url);
        if (matcher1.find(0) && matcher1.groupCount() > 0) {
            String time = matcher1.group(0);
            time = time.substring(1, time.length());
            Calendar theTime = Calendar.getInstance();
            String[] seg = time.split("[-|/|_]{1}");
            theTime.set(Calendar.YEAR, Integer.parseInt(seg[0]));
            theTime.set(Calendar.MONTH, Integer.parseInt(seg[1]));
            theTime.set(Calendar.DAY_OF_MONTH, 1);
            if (current.compareTo(theTime) >= 0) {
                return seg[0] + "-" + seg[1] + "-" + "01" + " " + "00:00:00";
            }
        }
        return null;
    }

    /**
     * 从网页源码中取出发布时间
     * java中正则表达式提取字符串中日期实现代码
     * 读取出2013-12-19 15:48:33或者2013-12-19或者2012/3/05形式的时间
     *
     * @param text 待提取的字符串
     * @return 返回日期
     */
    public static String extractPageDate(String text) {
        boolean containsHMS = false;
        String dateStr = text.replaceAll("r?n", " ");
        try {
            List<String> matches;
            //Matcher matcher = p.matcher(dateStr);
            Matcher matcher = pDetail.matcher(dateStr);

            if (!(matcher.find(0) && matcher.groupCount() >= 1)) {
                matcher = p.matcher(dateStr);
                containsHMS = true;
            } else {
                matcher = pDetail.matcher(dateStr);
            }
            if (matcher.find() && matcher.groupCount() >= 1) {
                matches = new ArrayList<>();
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    String temp = matcher.group(i);
                    matches.add(temp);
                }
            } else {
//                matches = Collections.EMPTY_LIST;
                matches = Collections.emptyList();
            }
            if (matches.size() > 0) {
                for (Object matche : matches) {
                    String pubTime = matche.toString().trim();
                    //取出第一个值
                    pubTime = pubTime.replace("/", "-").replace("年", "-").replace("月", "-").replace("日", "-");
                    if (current.compareTo(TimeUtils.strToCalendar(pubTime, "yyyy-MM-dd")) >= 0) {
                        if (containsHMS) {
                            pubTime += " " + "00:00:00";
                        }
                        if (pubTime.matches(rightTimeReg)) {
                            return pubTime;
                        }
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
