package thread;

import java.util.Date;

public class SpinLock {
    /*
     * http://www.tianshouzhi.com/api/tutorials/mutithread/111
     */
    public static volatile String sharedVariable;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
//					synchronized (SpinLock.class) {
                sharedVariable = "hello";
//					}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("start time:" + new Date());
        // 自旋锁，就是不断进行循环，起到阻塞的作用
        while (sharedVariable == null) {
            Thread.onSpinWait();
        }
        System.out.println(sharedVariable);
        System.out.println("end time:" + new Date());
    }

}
