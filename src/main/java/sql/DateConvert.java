package sql;

/**
 * Created by johnny on 2016/10/6.
 * convert between java.util.Date and java.sql.Date
 * 两个date都有一个相同的属性:time
	这个time代表的是从1970-1-1到现在long类型的毫秒数
	只需要在创建的时候将这个time设置给新类型的就能完成对时间类型的互转
 */
public class DateConvert {
    public static void main(String[] args) {
        java.util.Date jDate = new java.util.Date();
        java.sql.Date sDate = new java.sql.Date(jDate.getTime());
        System.out.println(jDate);
        System.out.println(sDate);

        java.sql.Date date = java.sql.Date.valueOf("2016-10-06");
        long time = date.getTime();
        java.util.Date uDate = new java.util.Date();
        uDate.setTime(time);
    }
}
