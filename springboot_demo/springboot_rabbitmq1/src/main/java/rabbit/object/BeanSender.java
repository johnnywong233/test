package rabbit.object;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import rabbit.entity.Person;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:58
 */
@Slf4j
@Component
public class BeanSender {
    @Resource
    private AmqpTemplate rabbitTemplate;

    public void send(Person person) {
        log.info("Sender object: " + person.toString());
        //发送到名为 object 的队列中。
        this.rabbitTemplate.convertAndSend("object", person);
    }
}
