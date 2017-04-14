package jmockit;

import mockit.Expectations;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Author: Johnny
 * Date: 2017/3/28
 * Time: 18:22
 * http://unmi.cc/jmockit-partial-mock/
 */
public class JmockitDemo {
    @Test
    public void testPartialMockingInstanceMethodsWithClass() {
        ClassToBeMocked anotherInstance = new ClassToBeMocked();

        final ClassToBeMocked mockedInstance = new ClassToBeMocked();
        new Expectations(ClassToBeMocked.class) {{
            mockedInstance.f3();  //任何 ClassToBeMocked 实例都受到影响，也包括上面的 anotherInstance
            result = "mocked";
        }};

        //mocked
        assertEquals("mocked", mockedInstance.f3());
        assertEquals("mocked", anotherInstance.f3());
        assertEquals("mocked", new ClassToBeMocked().f3());

        assertEquals("f1", ClassToBeMocked.f1());
        assertEquals("f4", mockedInstance.f4());
    }

    @Test
    public void testPartialMockingInstanceMethods() {
        final ClassToBeMocked mockedInstance = new ClassToBeMocked();
        new Expectations(mockedInstance) {{
            mockedInstance.f3(); //只有该实例的 f3() 方法受影响
            result = "mocked";
        }};

        //mocked
        assertEquals("mocked", mockedInstance.f3());

        assertEquals("f3", new ClassToBeMocked().f3());
        assertEquals("f1", ClassToBeMocked.f1());
        assertEquals("f4", mockedInstance.f4());
    }

    @Test
    public void testPartialMockingStaticMethods() {
        new Expectations(ClassToBeMocked.class) {{
            ClassToBeMocked.f1();  //只有该静态 f1() 方法受影响
            result = "mocked";
        }};

        //mocked
        assertEquals("mocked", ClassToBeMocked.f1());

        assertEquals("f2", ClassToBeMocked.f2());
        assertEquals("f3", new ClassToBeMocked().f3());
    }
}

class ClassToBeMocked {
     static String f1(){
        return "f1";
    }
     static String f2() {
        return "f2";
    }

     String f3() {
        return "f3";
    }

     String f4() {
        return "f4";
    }
}