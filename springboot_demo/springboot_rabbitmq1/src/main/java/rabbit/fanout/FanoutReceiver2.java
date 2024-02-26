package rabbit.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:51
 */
@Slf4j
@Component
public class FanoutReceiver2 {
    @RabbitHandler
    @RabbitListener(queues = "fanout.2")
    public void process(String message) {
        log.info("fanout Receiver 2  : " + message);
    }
}
