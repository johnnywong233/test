package sm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 21:26
 */
@Configuration
public class PersistConfig {
    @Resource
    private InMemoryStateMachinePersist inMemoryStateMachinePersist;

    @Bean(name = "myPersister")
    public StateMachinePersister<States, Events, String> getPersister() {
        return new DefaultStateMachinePersister<>(inMemoryStateMachinePersist);
    }
}
