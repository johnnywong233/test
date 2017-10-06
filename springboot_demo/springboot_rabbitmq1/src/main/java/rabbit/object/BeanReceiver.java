package rabbit.object;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbit.entity.Person;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:59
 */
@Component
@RabbitListener(queues = "object")
public class BeanReceiver {
    @RabbitHandler
    public void process(Person person) {
        System.out.println("Receiver object : " + person);
    }
}
