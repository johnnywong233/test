package utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: Johnny
 * Date: 2016/11/17
 * Time: 21:20
 */
public class DateUtil {

    /**
     * @description：获取现在时间
     **/
    public static Date getNow() {
        return new Date();
    }

    /**
     * @description： 获取现在日期时间
     * @return： 返回字符串格式 yyyy-MM-dd HH:mm:ss
     **/
    public static String getNowDateTimeStr() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(currentTime);
    }

    /**
     * @description： 获取现在时间 日期
     * @return： 返回字符串格式yyyyMMdd HHmmss
     **/
    public static String getNowDateTimeStrFormatTwo() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        return formatter.format(currentTime);
    }

    /**
     * @description： 获取现在时间 日期
     * @return： 返回字符串格式 yyyy-MM-dd
     **/
    public static String getNowDateStr() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(currentTime);
    }

    /**
     * @description： 获取现在时间
     * @return： 返回字符串格式 HH:mm:ss
     **/
    public static String getTimeStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * @description：日期时间字符串转日期时间格式
     * @return： 返回日期时间格式
     **/
    public static Date strToDateTime(String strDateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDateTime, pos);
    }

    /**
     * @description：日期字符串转日期格式
     * @return： 返回日期格式
     **/
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * @description：两个日期时间是否在跨度之内
     * @parameter： gapType 跨度类型，如Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_YEAR
     * @parameter： maxGap  最大跨度值
     * @return： 返回日期格式
     **/
    public static boolean isWithInDateGap(String startDate, String endDate,
                                          int gapType, int maxGap) {
        Date startDateTime;
        Date endDateTime;
        startDateTime = strToDateTime(startDate);
        endDateTime = strToDateTime(endDate);
        return isWithInDateGap(startDateTime, endDateTime, gapType, maxGap);
    }

    public static boolean isWithInDateGap(Date startDate, Date endDate,
                                          int gapType, int maxGap) {
        if (startDate == null) {
            throw new IllegalArgumentException("The startDate must not be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("The endDate must not be null");
        }
        if (gapType != Calendar.YEAR && gapType != Calendar.MONTH
                && gapType != Calendar.DAY_OF_YEAR) {
            throw new IllegalArgumentException(
                    "The value of gapType is invalid");
        }

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.add(gapType, maxGap);
        int compare = start.getTime().compareTo(endDate);
        return compare >= 0;
    }

    public static void main(String[] args) {
        System.out.println(getNow());
        System.out.println(getNowDateTimeStr());
        System.out.println(getNowDateTimeStrFormatTwo());
        System.out.println(getNowDateStr());
        System.out.println(getTimeStr());
        System.out.println(strToDateTime(getNowDateTimeStr()));
        System.out.println(strToDate(getNowDateStr()));
        System.out.println(isWithInDateGap(getNowDateTimeStr(), getNowDateTimeStr()
                , Calendar.YEAR, 1));
    }
}
