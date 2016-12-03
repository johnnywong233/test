package thread;

import java.util.Date;

public class SpinLock1 {
    /*
     * http://www.tianshouzhi.com/api/tutorials/mutithread/111
     * 有BUG
     */
    private static String sharedVariable;//共享变量

    public static void main(String[] args) {
        //启动一个线程执行运行
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);//进行运算操作，以休眠代替
                    sharedVariable = "hello";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.out.println("start time:" + new Date());
        //自旋锁，就是不断进行循环，起到阻塞的作用
        while (sharedVariable == null) {
//			System.out.println("bug?");
        }

        System.out.println(sharedVariable);

        //程序输出不是预期结果，应该是隔2秒之后出现end time ????????
        System.out.println("end time:" + new Date());
    }


}
