package log;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by wajian on 2016/8/18.
 */
//实现抽象方法 format()。它接收一个 LogRecord 对象作为参数，并返回一个有着日志信息 String 对象。
public class MyFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + record.getLevel() + "] - ");
        sb.append(new Date(record.getMillis()) + " : ");
        sb.append(record.getSourceClassName() + "." + record.getSourceMethodName() + " : ");
        sb.append(record.getMessage() + "\n");
        return sb.toString();
    }
}