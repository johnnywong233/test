package sm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 21:26
 */
@Configuration
public class PersistConfig {
    @Autowired
    private InMemoryStateMachinePersist inMemoryStateMachinePersist;

    @Bean(name = "myPersister")
    public StateMachinePersister<States, Events, String> getPersister() {
        return new DefaultStateMachinePersister<>(inMemoryStateMachinePersist);
    }
}
