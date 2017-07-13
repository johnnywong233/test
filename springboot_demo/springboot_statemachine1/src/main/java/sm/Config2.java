package sm;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 21:31
 */
@Configuration
@EnableStateMachine(name = "machine2")
public class Config2 extends Config {
}
