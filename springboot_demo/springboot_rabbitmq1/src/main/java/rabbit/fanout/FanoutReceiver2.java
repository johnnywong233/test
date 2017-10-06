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
public class FanoutReceiver2 {
    @RabbitHandler
@RabbitListener(queues = "fanout.2")
    public void process(String message) {
        System.out.println("fanout Receiver 2  : " + message);
    }
}
