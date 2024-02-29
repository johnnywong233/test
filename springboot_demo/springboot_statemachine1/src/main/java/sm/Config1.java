package sm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 21:31
 */
@Slf4j
@Configuration
@EnableStateMachine(name = "machine1")
public class Config1 extends Config {
    Config1() {
        log.info("machine1 init.");
    }
}
