package log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by wajian on 2016/8/18.
 */
public class LogDemo {
	//http://ifeve.com/testing-concurrent-applications-6-2/
    public static void main(String[] args) {
    	//16. 声明一个 Logger 对象，名为 logger。使用 MyLogger 类的 getLogger() 方法传递字符串 Core 作为参数来初始它
        Logger logger = MyLogger.getLogger("Core");

        //entering() 方法写日志信息表明主程序开始执行。
        logger.entering("Core", "main()", args);

        //create thread array
        Thread threads[] = new Thread[5];

        //19. 创建5个Task对象和5个执行他们的线程。写日志信息表明，你将运行一个新的线程和表明你已经创建了线程。
        for (int i = 0; i < threads.length; i++) {
            logger.log(Level.INFO, "Launching thread: " + i);
            Task task = new Task();
            threads[i] = new Thread(task);
            logger.log(Level.INFO,"Thread created:" + threads[i]. getName());
            threads[i].start();
        }
        logger.log(Level.INFO, "Ten Threads created." + "Waiting for its finalization");

        //21. 使用 join() 方法等待5个线程的终结。在每个线程终结之后，写日志信息表明线程已经结束。
        for (int i=0; i<threads.length; i++) {
            try {
                threads[i].join();
                logger.log(Level.INFO, "Thread has finished its execution", threads[i]);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Exception", e);
            }
        }
        //exiting() to note the end of main thread
        logger.exiting("Core", "main()");
    }
}