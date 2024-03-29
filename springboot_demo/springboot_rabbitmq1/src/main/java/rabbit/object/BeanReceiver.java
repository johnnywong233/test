package rabbit.object;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbit.entity.Person;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:59
 */
@Slf4j
@Component
//通过 @RabbitListener 注解定义该类对 object 队列的监听，并用 @RabbitHandler 注解来指定对消息的处理方法。
@RabbitListener(queues = "object")
public class BeanReceiver {
    @RabbitHandler
    public void process(Person person) {
        log.info("Receiver object : " + person);
    }
}
