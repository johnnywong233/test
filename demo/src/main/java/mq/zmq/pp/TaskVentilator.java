package mq.zmq.pp;

import org.zeromq.ZMQ;

import java.util.Random;

/**
 * Author: Johnny
 * Date: 2017/10/3
 * Time: 10:54
 */
public class TaskVentilator {
    public static void main(String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);
        //socket to send message on
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.bind("tcp://*:5557");
        ZMQ.Socket sink = context.socket(ZMQ.PUSH);
        sink.connect("tcp://localhost:5558");
        System.out.println("Press Enter when the workers are ready:");
        System.in.read();
        System.out.println("Sending task to worker\n");
        sink.send("0", 0);
        Random random = new Random(System.currentTimeMillis());
        int taskNbr;
        int totalMsec = 0;
        for (taskNbr = 0; taskNbr < 100; taskNbr++) {
            int worklad;
            worklad = random.nextInt(100) + 1;
            totalMsec += worklad;
            System.out.println(worklad + ".");
            String str = String.format("%d" + worklad);
            sender.send(str, 0);
        }
        System.out.println("Total expected cost: " + totalMsec + "msec");
        Thread.sleep(1000);
        sink.close();
        sender.close();
        context.term();
    }
}
