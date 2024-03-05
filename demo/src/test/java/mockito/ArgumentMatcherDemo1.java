package mockito;

import org.mockito.ArgumentMatcher;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


/**
 * Author: Johnny
 * Date: 2017/3/8
 * Time: 19:00
 */
@SuppressWarnings("unchecked")
public class ArgumentMatcherDemo1 {

    @Test
    public void argumentMatcherTest() {
        List<String> mock = mock(List.class);
        //IncompatibleClassChangeError
        when(mock.addAll(argThat(new IsListOfTwoElements()))).thenReturn(true);

        mock.addAll(Arrays.asList("one", "two"));
        verify(mock).addAll(argThat(new IsListOfTwoElements()));
    }

    @Test
    public void argumentMatcherTest1() {
        List<String> mock = mock(List.class);
        when(mock.get(anyInt())).thenReturn("Hello").thenReturn("World");
        String result = mock.get(100) + " " + mock.get(200);
        verify(mock, times(2)).get(anyInt());
        assertEquals("Hello World", result);
    }

    @Test
    public void argumentMatcherTest2() {
        Map<Integer, String> mapMock = mock(Map.class);
        when(mapMock.put(anyInt(), anyString())).thenReturn("world");
        mapMock.put(1, "hello");
        verify(mapMock).put(anyInt(), eq("hello"));//must use eq()
    }

}

/**
 * self defined ArgumentMatcher
 */
@SuppressWarnings("rawtypes")
class IsListOfTwoElements implements ArgumentMatcher<List> {
    @Override
    public boolean matches(List list) {
        return list.size() == 2;
    }

    @Override
    public Class<?> type() {
        return ArgumentMatcher.super.type();
    }
}