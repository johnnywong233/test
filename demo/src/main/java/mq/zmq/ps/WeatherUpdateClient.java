package mq.zmq.ps;

import org.zeromq.ZMQ;

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
        int update_nbr;
        long total_temp = 0;
        int zipCode = 0;
        int humidity = 0;
        for (update_nbr = 0; update_nbr < 100; update_nbr++) {
            String str = subscriber.recvStr(0).trim();
            StringTokenizer tokenizer = new StringTokenizer(str, " ");
            zipCode = Integer.valueOf(tokenizer.nextToken());
            int temperature = Integer.valueOf(tokenizer.nextToken());
            humidity = Integer.valueOf(tokenizer.nextToken());
            total_temp += temperature;
        }
        System.out.println("zipCode" + zipCode + "humidity" + humidity + "Average temperature for zipCode '" + filter + "' was " + total_temp / update_nbr);
        subscriber.close();
        context.term();
    }

}