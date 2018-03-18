package rabbit.object;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rabbit.entity.Person;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 7:58
 */
@Component
public class BeanSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(Person person) {
        System.out.println("Sender object: " + person.toString());
        //发送到名为 object 的队列中。
        this.rabbitTemplate.convertAndSend("object", person);
    }
}
