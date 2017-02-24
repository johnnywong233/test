package useless;

import org.joda.time.LocalDateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Calendar;

/**
 * Author: Johnny
 * Date: 2017/2/12
 * Time: 13:33
 */
public class ConstellationBuilder {
    public static String getAstro(int month, int day) {
        String[] starArr = {"魔羯座", "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座",
                "处女座", "天秤座", "天蝎座", "射手座"};
        int[] DayArr = {22, 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22};  //两个星座分割日
        int index = month;// 所查询日期在分割日之前，索引-1，否则不变
        if (day < DayArr[month - 1]) {
            index = index - 1;
        }
        return starArr[index];
    }

    //http://www.jianshu.com/p/cbcf78360308
    public static void main(String[] args) {
        System.out.println(getAstro(4, 28));
    }

    @Test
    public void getAstro() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1988, Calendar.OCTOBER, 16);
        LocalDateTime localDateTime = LocalDateTime.fromDateFields(calendar.getTime());
        String res = ConstellationBuilder.getAstro(localDateTime.getMonthOfYear(), localDateTime.getDayOfMonth());
        Assert.assertEquals("天秤座", res);
    }
}
