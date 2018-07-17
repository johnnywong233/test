package simpletest;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Johnny
 * Date: 2017/4/7
 * Time: 17:37
 */
public class TimerTest1 {

    private Timer timer;

    public long start;

    private TimerTest1(){
        this.timer = new Timer();
        start = System.currentTimeMillis();
    }

    private void timerOne(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timerOne invoked, the time:" + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(4000);
                    //线程休眠4000
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    private void timerTwo(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timerTwo invoked, the time:" + (System.currentTimeMillis() - start));
            }
        }, 3000);
    }

    //http://www.cnblogs.com/chenssy/p/3788407.html
    public static void main(String[] args) throws Exception {
        TimerTest1 test = new TimerTest1();
        test.timerOne();
        test.timerTwo();
    }

}
