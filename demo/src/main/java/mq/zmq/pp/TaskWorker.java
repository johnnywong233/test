package mq.zmq.pp;

import org.zeromq.ZMQ;

import java.io.IOException;

/**
 * Author: Johnny
 * Date: 2017/10/3
 * Time: 11:02
 */
public class TaskWorker {
    public static void main(String[] args) throws IOException, InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        //socket to receive message on
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.connect("tcp://localhost:5557");
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.connect("tcp://localhost:5558");
        while (!Thread.currentThread().isInterrupted()) {
            String str = new String(receiver.recv(0)).trim();
            long msec = Long.parseLong(str);
            //simple process indicator for the viewer
            System.out.flush();
            System.out.println(str + ".");
            Thread.sleep(msec);
            //send results to sink
            sender.send("".getBytes(), 0);
        }
        sender.close();
        receiver.close();
        context.term();
    }
}
