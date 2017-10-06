package rabbit.fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:53
 */
@Component
public class FanoutSender2 {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(Integer object) {
        System.out.println("Sender2 : " + object);
        this.rabbitTemplate.convertAndSend("fanoutExchange", "", object);
    }
}
