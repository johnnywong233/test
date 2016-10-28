package concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Created by wajian on 2016/8/22.
 */
public class ExecutorServiceDemo {
    //http://www.jb51.net/article/62912.htm
    public static void main(String args[]){

//        for(int i = 0; i < 10; i++){
//            Elem e = new Elem();
//            if(i == 0 )
//                e.setPriority(Thread.MAX_PRIORITY);
//            else
//                e.setPriority(Thread.MIN_PRIORITY);
//            Thread t = new Thread(e);
//            t.start();
//        }

        //use ExecutorService to realize thread pool tech
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++){
            Elem e = new Elem();
            if(i == 0 )
                e.setPriority(Thread.MAX_PRIORITY);
            else
                e.setPriority(Thread.MIN_PRIORITY);
            exec.execute(e);
        }
        exec.shutdown();
    }
}

class Elem implements Runnable{
    public static int id = 0;
    private int cutDown = 5;
    private int priority;

    public void setPriority(int priority){
        this.priority = priority;
    }

    public int getPriority(){
        return this.priority;
    }
    public void run(){
        Thread.currentThread().setPriority(priority);
        int threadId = id++;
        while(cutDown-- > 0){
            double d = 1.2;
            while(d < 10000)
                d = d + (Math.E + Math.PI)/d;
            System.out.println("#" + threadId + "(" + cutDown + ")");
        }
    }
}