package thread;

import java.util.Date;

public class SpinLock1 {
    /*
     * http://www.tianshouzhi.com/api/tutorials/mutithread/111
     */
    private static String sharedVariable;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                sharedVariable = "world";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("start time:" + new Date());
        // let it be
        while (sharedVariable == null) {
            Thread.onSpinWait();
        }
        System.out.println(sharedVariable);
        System.out.println("end time:" + new Date());
    }


}
