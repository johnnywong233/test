package rabbit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rabbit.entity.Person;
import rabbit.object.BeanSender;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 8:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanTest {
    @Autowired
    private BeanSender sender;

    @Test
    public void sendObject() throws Exception {
        Person person = new Person();
        person.setName("johnny");
        person.setAge(111);
        sender.send(person);
    }
}
