package rabbit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rabbit.fanout.FanoutSender1;
import rabbit.fanout.FanoutSender2;

/**
 * Author: Johnny
 * Date: 2017/10/6
 * Time: 8:01
 */
@SpringBootTest
public class FanoutTest {
    @Autowired
    private FanoutSender1 sender1;

    @Autowired
    private FanoutSender2 sender2;

    @Test
    public void oneToMany() {
        for (int i = 0; i < 10; i++) {
            sender1.send(i);
        }
    }

    @Test
    public void manyToMany() {
        for (int i = 0; i < 10; i++) {
            sender1.send(i);
            sender2.send(i);
        }
    }
}