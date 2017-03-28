package mockito;

import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;


/**
 * Author: Johnny
 * Date: 2017/3/8
 * Time: 16:14
 */
public class SpyDemo1 {

    @Test
    public void spyTest2() {

        List<String> list = new LinkedList<>();
        List<String> spy = spy(list);
        
        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        //using the spy calls real methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        System.out.println(spy.get(0));

        //size() method was stubbed - 100 is printed
        System.out.println(spy.size());

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");
    }

    /**
     * 当调用when(spy.get(0)).thenReturn("foo")时，会调用真实对象的get(0)，由于list是空的所以会抛出IndexOutOfBoundsException异常
     * 用doReturn可以避免这种情况的发生，因为它不会去调用get(0)方法。
     */
    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void spyTest1(){
        List<String> spy = spy(new LinkedList<>());
        //Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)

        when(spy.get(0)).thenReturn("foo");
        //You have to use doReturn() for stubbing
        doReturn("foo").when(spy).get(0);
    }

}
