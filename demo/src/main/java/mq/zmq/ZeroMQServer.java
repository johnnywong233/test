package mq.zmq;

import org.zeromq.ZMQ;

/**
 * Author: Johnny
 * Date: 2017/10/3
 * Time: 9:50
 */
public class ZeroMQServer {
    public static void main(String[] args) throws InterruptedException {
        ZMQ.Context context = ZMQ.context(1);
        //Socket to talk to client
        ZMQ.Socket responder = context.socket(ZMQ.REQ);
        responder.bind("tcp://*:5555");
        while (!Thread.currentThread().isInterrupted()) {
            //wait for the next request from the client
            System.out.println("received hello");
            Thread.sleep(1000);
            String reply = "johnny";
            //send reply back to client
            responder.send(reply.getBytes(), 0);
        }
        responder.close();
        context.term();
    }
}
