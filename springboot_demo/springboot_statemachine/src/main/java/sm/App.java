package sm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 0:48
 */
@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    private StateMachine<States, Events> stateMachine;

    //http://blog.csdn.net/baochanghong/article/details/54286298
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.RECEIVE);
    }
}
