package sm;

import org.junit.Test;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 21:33
 */
public class SimpleTest extends StatemachinePersistApplicationTests {

    @Resource(name = "machine1")
    private StateMachine<States, Events> stateMachine1;

    @Resource(name = "machine2")
    private StateMachine<States, Events> stateMachine2;

    @Resource(name = "myPersister")
    private StateMachinePersister<States, Events, String> persister;

    @Test
    //TODO: NPE
    public void testSimple() throws Exception {
        stateMachine1.start();
        stateMachine1.sendEvent(Events.A);
        persister.persist(stateMachine1, "mySM");
        persister.restore(stateMachine2, "mySM");
        System.out.println(stateMachine2.getState().getId());
    }
}
