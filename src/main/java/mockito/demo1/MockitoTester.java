package mockito.demo1;

import org.junit.Test;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by wajian on 2016/10/10.
 */
public class MockitoTester {
    //see more at http://unmi.cc/jmockit-partial-mock/
    @Test
    public void should_run_customer_mockito_matcher() throws Exception {

        final GameDao gameDao = mock(GameDao.class);
        gameDao.addRate(new Game("签到", 7));

        verify(gameDao).addRate(argThat(new PartyMatcher<Game>(new Function<Game, Object>() {
            @Override
            public Object apply(Game game) {
                return game.getRate();
            }
        }, 7)));

        verify(gameDao).addRate(argThat(new PartyMatcher<Game>(new Function<Game, Object>() {
            @Override
            public Object apply(Game game) {
                return game.getType();
            }
        }, "签到")));
    }

}
