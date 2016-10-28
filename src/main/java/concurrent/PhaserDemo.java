package concurrent;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Created by wajian on 2016/8/18.
 */
public class PhaserDemo {

	//http://ifeve.com/testing-concurrent-applications-3/
    public static void main(String[] args) throws Exception {

    	//9.   创建新的有3个参与者的 Phaser 对象，名为 phaser。
    	final Integer N = 3;
        Phaser phaser = new Phaser(N);

        //10. 创建并运行3个线程来执行3个task对象。
        for (int i=0; i<N; i++) {
        	MyTask task = new MyTask(i+1, phaser);
            Thread thread = new Thread(task);
            thread.start();
        }

        //11.创建迭代10次的for循环，来学关于phaser对象的信息。
        for (int i=0; i<10; i++) {

        	//12. 写关于 registered parties 的信息，phaser的phase，到达的parties, 和未到达的parties 的信息。
            System.out.printf("********************\n");
            System.out.printf("Main: Phaser Log\n");
            System.out.printf("Main: Phaser: Phase: %d\n",phaser.getPhase());
            System.out.printf("Main: Phaser: Registered Parties:%d\n",phaser.getRegisteredParties());
            System.out.printf("Main: Phaser: Arrived Parties:%d\n",phaser.getArrivedParties());
            System.out.printf("Main: Phaser: Unarrived Parties:%d\n",phaser.getUnarrivedParties());
            System.out.printf("********************\n");

            //合上类的循环
            TimeUnit.SECONDS.sleep(1);
        }
    }
}

class MyTask implements Runnable {

    private int time;

    private Phaser phaser;

    public MyTask(int time, Phaser phaser) {
        this.time = time;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        phaser.arrive();
//6.   写信息到操控台表明阶段一开始，把线程放入休眠几秒，使用time属性来表明，再写信息到操控台表明阶段一结束，并使用 phaser 属性的 arriveAndAwaitAdvance() 方法来与剩下的任务同步。
        System.out.printf("%s: Entering phase 1.\n",Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: Finishing phase 1.\n",Thread.currentThread().getName());
        phaser.arriveAndAwaitAdvance();

//7.	为第二和第三阶段重复第一阶段的行为。在第三阶段的末端使用 arriveAndDeregister()方法代替 arriveAndAwaitAdvance() 方法。
        System.out.printf("%s: Entering phase 2.\n",Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: Finishing phase 2.\n",Thread.currentThread().getName());
        phaser.arriveAndAwaitAdvance();

        System.out.printf("%s: Entering phase 3.\n",Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: Finishing phase 3.\n",Thread.currentThread().getName());
        phaser.arriveAndDeregister();
    }
}

