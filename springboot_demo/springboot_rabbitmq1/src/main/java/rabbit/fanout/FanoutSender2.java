package rabbit.fanout;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:53
 */
@Slf4j
@Component
public class FanoutSender2 {
    @Resource
    private AmqpTemplate rabbitTemplate;

    public void send(Integer object) {
        log.info("Sender2 : " + object);
        this.rabbitTemplate.convertAndSend("fanoutExchange", "", object);
    }
}
