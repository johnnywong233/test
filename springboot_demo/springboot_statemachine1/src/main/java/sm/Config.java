package sm;

import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 21:27
 */
public class Config extends StateMachineConfigurerAdapter<States, Events> {
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.S0)
                .state(States.S0)
                .state(States.S1)
                .state(States.S2);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.S0)
                .target(States.S1)
                .event(Events.A)
                .and()
                .withExternal()
                .source(States.S1)
                .target(States.S0)
                .event(Events.E)
                .and()
                .withExternal()
                .source(States.S1)
                .target(States.S2)
                .event(Events.B);
    }

}
