package log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by wajian on 2016/8/18.
 */
public class MyLogger {
    //3.   声明一个私有 static Handler 属性，名为 handler。
    public static Handler handler;

    //4.   实现公共 static 方法 getLogger() 来创建 Logger 对象，你将要使用它来写日志信息。它接收一个String 参数，名为 name。
    public static Logger getLogger(String name) {

        //5.   使用 Logger 类的getLogger() 方法,获取与 java.util.logging.Logger 相关联的 name 作为参数。
        Logger logger = Logger.getLogger(name);

        //6.   使用 setLevel() 方法，确立用来写入全部信息的log级别。
        logger.setLevel(Level.ALL);

        //7.	如果处理者属性为null值，创建一个新的 FileHandler 对象在 recipe8.log 文件内写日志信息。使用 setFormatter()对象给处理者分配一个 MyFormatter  对象作为格式。
        try {
            if (handler == null) {
                handler = new FileHandler("recipe8.log");
                Formatter format = new MyFormatter();
                handler.setFormatter(format);
            }

            //8.   If the 如果 Logger 对象还有一个与之相关联的处理者，使用 addHandler() 方法分配一个处理者。
            if (logger.getHandlers().length == 0) {
                logger.addHandler(handler);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //9.   返回创建的 Logger 对象。
        return logger;
    }
}
