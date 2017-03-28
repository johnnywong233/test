package mockito;

import org.mockito.ArgumentCaptor;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

/**
 * Author: Johnny
 * Date: 2017/3/8
 * Time: 16:26
 */
@SuppressWarnings("unchecked")
public class ArgumentCaptorDemo1 {
    @Test
    public void argumentCaptorTest() {
        List<String> mock = mock(List.class);
        List<String> mock2 = mock(List.class);
        mock.add("John");
        mock2.add("Brian");
        mock2.add("Jim");

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

        verify(mock).add(argument.capture());
        assertEquals("John", argument.getValue());

        verify(mock2, times(2)).add(argument.capture());

        assertEquals("Jim", argument.getValue());//getValue will return the last capture string
        assertArrayEquals(new Object[]{"John", "Brian", "Jim"}, argument.getAllValues().toArray());//有顺序
    }

}