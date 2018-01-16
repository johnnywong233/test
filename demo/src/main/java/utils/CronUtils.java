package utils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Johnny on 2018/1/8.
 */
public class CronUtils {
    /**
     * 判断 <code>date</code> 是否在 <code>crons</code> 所指定的范围内
     */
    public static boolean isDuarationSatisfied(List<String> crons, Date date) throws ParseException {
        boolean isDurationSatisfied = false;
        if (crons != null && crons.size() > 0) {
            for (String cron : crons) {
                CronExpression expr = new CronExpression(cron);
                isDurationSatisfied = expr.isSatisfiedBy(date);
                if (isDurationSatisfied) {
                    break;
                }
            }
        }
        return isDurationSatisfied;
    }
}
