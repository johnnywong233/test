package mockito;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.mockito.InOrder;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

//import static org.mockito.hamcrest.MockitoHamcrest.argThat;

/**
 * Author: Johnny Date: 2017/2/21 Time: 18:20
 */
@SuppressWarnings("unchecked")
public class MockitoDemo1 {

    //http://blog.csdn.net/renyibb/article/details/52219629
    //https://waylau.com/mockito-quick-start/
    @Test
    public void test() {
        // 创建mock对象
        List<String> mockedList = mock(List.class);

        // 使用mock对象
        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
        // mock对象一旦被创建，会记住所有的验证，并在你感兴趣的时候提供可选的校验

        // using mock
        mockedList.add("once");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        // following two verifications work exactly the same - times(1) is used
        // by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        // exact number of invocations verification
        verify(mockedList, times(3)).add("three times");

        // verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        // verification using atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");

        //没有返回值类型的方法也可以模拟异常抛出
        doThrow(new RuntimeException()).when(mockedList).clear();


        // 参数匹配
        // stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        // stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
//         when(mockedList.contains(argThat(isValid()))).thenReturn("element");

        // following prints "element"
        System.out.println(mockedList.get(999));

        // you can also verify using an argument matcher
        verify(mockedList).get(anyInt());

        // argument matchers can also be written as Java 8 Lambdas
        // verify(mockedList).add(someString -> someString.length() > 5);


        // You can mock concrete classes, not just interfaces
        LinkedList<String> mockedLinkedList = mock(LinkedList.class);

        // stubbing
        when(mockedLinkedList.get(0)).thenReturn("first");
        when(mockedLinkedList.get(1)).thenThrow(new RuntimeException());

        // following prints "first"
        System.out.println(mockedLinkedList.get(0));

        // following throws runtime exception
        // org.mockito.exceptions.misusing.UnfinishedStubbingException: Unfinished stubbing detected here:
        // System.out.println(mockedLinkedList.get(1));
        // doThrow(new RuntimeException()).when(mockedLinkedList.get(1));

        // following prints "null" because get(999) was not stubbed
        //此时调用get方法，是会返回null，因为还没有对方法调用的返回值做模拟
        System.out.println(mockedLinkedList.get(999));

        // Although it is possible to verify a stubbed invocation, usually it's
        // just redundant
        // If your code cares what get(0) returns, then something else breaks
        // (often even before verify() gets executed).
        // If your code doesn't care what get(0) returns, then it should not be
        // stubbed. Not convinced? See here.
        verify(mockedLinkedList).get(0);


        List<String> singleMock = mock(List.class);
        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);

        //following will make sure that add is first called with "was added first, then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List<String> firstMock = mock(List.class);
        List<String> secondMock = mock(List.class);

        firstMock.add("was called first");
        secondMock.add("was called second");

        //following verification will fail
//        verifyNoMoreInteractions(firstMock);

        //create inOrder object passing any mocks that need to be verified in order
        inOrder = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");
        // Oh, and A + B can be mixed together at will


        //using mocks - only mockOne is interacted
        firstMock.add("one");
        verify(firstMock).add("one");

        //verify that method was never called on a mock
        verify(firstMock, never()).add("two");

        //verify that other mocks were not interacted
        verifyZeroInteractions(firstMock, secondMock);

        verify(firstMock).add("was called first");
        verifyNoMoreInteractions(firstMock);

    }

    @Test
    public void iterator_will_return_hello_world() {
        //arrange
        Iterator<String> i = mock(Iterator.class);
        when(i.next()).thenReturn("Hello").thenReturn("World");
        //act
        String result = i.next() + " " + i.next();
        //assert
        assertEquals("Hello World", result);
    }

    @Test
    public void with_arguments() {
        Comparable<String> c = mock(Comparable.class);
        when(c.compareTo("Test")).thenReturn(1);
        assertEquals(1, c.compareTo("Test"));
    }

    //JUnit uses expected = IOException.class
    @Test(expectedExceptions = IOException.class)
    public void OutputStreamWriter_rethrows_an_exception_from_OutputStream()
            throws IOException {
        OutputStream mock = mock(OutputStream.class);
        OutputStreamWriter osw = new OutputStreamWriter(mock);
        doThrow(new IOException()).when(mock).close();
        osw.close();
    }

    @Test
    public void OutputStreamWriter_Closes_OutputStream_on_Close()
            throws IOException {
        OutputStream mock = mock(OutputStream.class);
        OutputStreamWriter osw = new OutputStreamWriter(mock);
        osw.close();
        verify(mock).close();
    }

    //    https://gojko.net/2009/10/23/mockito-in-six-easy-examples/
    @Test
    public void OutputStreamWriter_Buffers_And_Forwards_To_OutputStream()
            throws IOException {
        OutputStream mock = mock(OutputStream.class);
        OutputStreamWriter osw = new OutputStreamWriter(mock);
        osw.write('a');
        osw.flush();
        // can't do this as we don't know how long the array is going to be
        // verify(mock).write(new byte[]{'a'},0,1);

        BaseMatcher<byte[]> arrayStartingWithA = new BaseMatcher<>() {
            @Override
            public void describeTo(Description description) {
                // nothing
            }

            // check that first character is A
            @Override
            public boolean matches(Object item) {
                byte[] actual = (byte[]) item;
                return actual[0] == 'a';
            }
        };
        // check that first character of the array is A,
        // and that the other two arguments are 0 and 1
        verify(mock).write(argThat(arrayStartingWithA), eq(0), eq(1));
    }

}
