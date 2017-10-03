package mq.zmq;

import org.zeromq.ZMQ;

import java.util.Arrays;

/**
 * Author: Johnny
 * Date: 2017/10/3
 * Time: 10:00
 */
public class ZeroMQClient {
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        System.out.println("Connecting to hello johnny sever");
        ZMQ.Socket requester = context.socket(ZMQ.REQ);
        requester.connect("tcp://localhost:5555");
        for (int requestNbr = 0; requestNbr != 10; requestNbr++) {
            String request = "hello";
            System.out.println("Sending hello" + requestNbr);
            requester.send(request.getBytes(), 0);
            byte[] reply = requester.recv(0);
            System.out.println("Received " + Arrays.toString(reply) + requestNbr);
        }
        requester.close();
        context.term();
    }
}