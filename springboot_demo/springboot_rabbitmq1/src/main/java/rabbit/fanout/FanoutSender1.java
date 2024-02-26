package rabbit.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:53
 */
@Slf4j
@Component
public class FanoutSender1 {
    @Resource
    private AmqpTemplate rabbitTemplate;

    public void send(Integer object) {
        log.info("Sender : " + object);
        this.rabbitTemplate.convertAndSend("fanoutExchange", "", object);
    }
}
