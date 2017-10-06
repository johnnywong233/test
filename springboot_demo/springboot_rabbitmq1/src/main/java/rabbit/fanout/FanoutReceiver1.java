package rabbit.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:51
 */
@Component
public class FanoutReceiver1 {
    @RabbitHandler
@RabbitListener(queues = "fanout.1")
    public void process(String message) {
        System.out.println("fanout Receiver 1  : " + message);
    }
}
