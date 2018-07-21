package mq.zmq.pp;

import org.zeromq.ZMQ;

/**
 * Author: Johnny
 * Date: 2017/10/3
 * Time: 11:29
 */
public class TaskSink {
    public static void main(String[] args) {
        //prepare
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.bind("tcp://*:5558");
        String str = new String(receiver.recv(0));
        long start = System.currentTimeMillis();
        int taskNbr;
        for (taskNbr = 0; taskNbr < 100; taskNbr++) {
            str = new String(receiver.recv(0)).trim();
            if ((taskNbr / 10) * 10 == taskNbr) {
                System.out.println(":");
            } else {
                System.out.println(".");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(str);
        System.out.println("Total elapsed time: " + (end - start) + "msec");
        receiver.close();
        context.term();
    }
}
