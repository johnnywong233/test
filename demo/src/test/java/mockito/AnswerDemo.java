package mockito;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

/**
 * Author: Johnny
 * Date: 2017/3/8
 * Time: 18:51
 */
public class AnswerDemo implements Answer<String> {
    public String answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        Integer num = (Integer) args[0];
        if (num > 3) {
            return "yes";
        } else {
            throw new RuntimeException();
        }
    }

    @SuppressWarnings("unchecked")
	@Test
    public void customAnswerTest() {
        List<String> mock = mock(List.class);
        when(mock.get(4)).thenAnswer((Answer<String>) invocation -> {
            Object[] args = invocation.getArguments();
            Integer num = (Integer) args[0];
            if (num > 3) {
                return "yes";
            } else {
                throw new RuntimeException();
            }
        });
        System.out.println(mock.get(4));
    }
}
