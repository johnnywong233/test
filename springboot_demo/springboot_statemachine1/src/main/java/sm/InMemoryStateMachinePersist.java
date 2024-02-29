package sm;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 21:26
 */
@Component
public class InMemoryStateMachinePersist implements StateMachinePersist<States, Events, String> {
    private static final HashMap<String, StateMachineContext<States, Events>> map = new HashMap<>();

    @Override
    public void write(StateMachineContext<States, Events> context, String contextObj) {
        map.put(contextObj, context);
    }

    @Override
    public StateMachineContext<States, Events> read(String contextObj) {
        return map.get(contextObj);
    }
}
