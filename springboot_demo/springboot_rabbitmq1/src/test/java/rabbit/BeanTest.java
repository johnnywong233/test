package rabbit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rabbit.entity.Person;
import rabbit.object.BeanSender;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 8:05
 */
@SpringBootTest
public class BeanTest {
    @Autowired
    private BeanSender sender;

    @Test
    public void sendObject() {
        Person person = new Person();
        person.setName("johnny");
        person.setAge(111);
        sender.send(person);
    }
}
