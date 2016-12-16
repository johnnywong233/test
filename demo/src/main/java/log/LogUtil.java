package log;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by wajian on 2016/8/30.
 *
 */
public class LogUtil {
    // http://www.phpxs.com/code/1001617/

    protected Logger logger;
    // 将Log类封装成单实例的模式，独立于其他类。以后要用到日志的地方只要获得Log的实例就可以方便使用
    private static LogUtil log;

    // 构造函数，用于初始化Logger配置需要的属性
    private LogUtil(Logger log4jLogger) {
        // 获得当前目录路径
        // String filePath=this.getClass().getResource("/").getPath();
        // 找到log4j.properties配置文件所在的目录(已经创建好)
        // filePath=filePath.substring(1).replace("bin", "src");
        // 获得日志类logger的实例
        // loger=Logger.getLogger(this.getClass());
        logger = log4jLogger;

        // logger所需的配置文件路径
        PropertyConfigurator.configureAndWatch("/u01/logs/log4j.properties");
    }

    /**
     * 获取构造器，根据类初始化Logger对象
     *
     * @param classObject Class对象
     * @return Logger对象
     */
    public static LogUtil getLogger(Class<?> classObject) {
        if (log != null)
            return log;
        else
            return new LogUtil(Logger.getLogger(classObject));
    }

    /*
     * 该事件ID包含当前时间和主机IP,是标示日志时间的唯一符
     */
    protected String eventID() {
        // 获取当前时间
        // 如果不需要格式,可直接用当前系统时间dt
        Date dt = new Date();
        // 设置显示格式,24小时制
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String nowTime = df.format(dt);// 用DateFormat的format()方法在dt中获取并以yyyy/MM/dd
        // HH:mm:ss格式显示

        String nowIP;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            nowIP = addr.getHostAddress();
        } catch (Exception ex) {
            nowIP = "";
        }
        nowTime = nowTime.replaceAll("/", "");
        nowTime = nowTime.replaceAll(" ", "");
        nowTime = nowTime.replaceAll(":", "");
        nowIP = nowIP.replace(".", "");
        return nowTime + nowIP;
    }

    public void debug(String KeyWord, String Content) {
        if (logger.isDebugEnabled()) {
            String message = " " + KeyWord + " " + Content;
            logger.debug(message);
        }
    }

    public void debug(String Content) {
        if (logger.isDebugEnabled()) {
            logger.debug(Content);
        }
    }

    public void fatal(String KeyWord, String Content) {
        String message = " " + KeyWord + " " + Content;
        logger.fatal(message);
    }

    public void fatal(String Content) {
        logger.fatal(Content);
    }

    public void info(String KeyWord, String Content) {
        if (logger.isInfoEnabled()) {
            String message = KeyWord + " " + Content;
            logger.info(message);
        }
    }

    public void info(String Content) {
        if (logger.isInfoEnabled())
            logger.info(Content);
    }

    public void warn(String KeyWord, String Content) {
        String message = KeyWord + " " + Content;
        logger.warn(message);
    }

    public void warn(String Content) {
        logger.warn(Content);
    }

    public void error(String KeyWord, String Content) {
        String message1 = KeyWord + " " + Content;
        logger.error(message1);
    }

    public void error(String Content) {
        logger.error(Content);
    }
}
