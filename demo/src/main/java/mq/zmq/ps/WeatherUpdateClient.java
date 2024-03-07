package mq.zmq.ps;

import org.zeromq.ZMQ;

import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Author: Johnny
 * Date: 2017/10/3
 * Time: 10:43
 */
public class WeatherUpdateClient {
    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        //socket to talk to server
        System.out.println("Collecting updates from server.");
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect("tcp://localhost:5556");
        String filter = (args.length > 0) ? args[0] : "10001";
        subscriber.subscribe(filter.getBytes());
        int updateNbr;
        long total = 0;
        int zipCode = 0;
        int humidity = 0;
        for (updateNbr = 0; updateNbr < 100; updateNbr++) {
            String str = Arrays.toString(subscriber.recv(0)).trim();
            StringTokenizer tokenizer = new StringTokenizer(str, " ");
            zipCode = Integer.parseInt(tokenizer.nextToken());
            int temperature = Integer.parseInt(tokenizer.nextToken());
            humidity = Integer.parseInt(tokenizer.nextToken());
            total += temperature;
        }
        System.out.println("zipCode" + zipCode + "humidity" + humidity + "Average temperature for zipCode '" + filter + "' was " + total / updateNbr);
        subscriber.close();
        context.term();
    }

}