package mq.zmq.ps;

import org.zeromq.ZMQ;

import java.util.Random;

/**
 * Author: Johnny
 * Date: 2017/10/3
 * Time: 10:14
 */
public class WeatherUpdateServer {
    public static void main(String[] args) {
        //prepare context and publisher
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://*:5556");
        //publisher.bind("ipc://weather");
        //initialize random number generator
        Random random = new Random(System.currentTimeMillis());
        while (!Thread.currentThread().isInterrupted()) {
            int zipCode, temperature, humidity;
            zipCode = 10000 + random.nextInt(10000);
            temperature = random.nextInt(215) - 80 + 1;
            humidity = random.nextInt(50) + 10 + 1;
            //send message to all subscribers
            String update = String.format("%05d %d %d", zipCode, temperature, humidity);
            publisher.send(update, 0);
        }
        publisher.close();
        context.term();
    }
}
