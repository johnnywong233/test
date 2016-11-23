package test.testng;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Created by wajian on 2016/10/9.
 */
//@Listeners(value=TestListener.class)
public class TestListenerExample {
    @BeforeTest
    public void beforeTest() {
        System.out.println("before test");
    }

    @Test
    public void t1() {
        System.out.println("t1 test method");
    }

    @Test(expectedExceptions=RuntimeException.class)
    public void t2() {
        System.out.println("t2 test method will fail");
    }

    @Test
    public void t3(String p) {
        System.out.println("t3 test method will skip as parameter p is not set");
    }

    @Test(successPercentage=80, invocationCount=5)
    public void t4() {
        i++;
        System.out.println("t4 test method, invocation count: " + i);
        if (i == 1 || i == 2) {
            System.out.println("fail t4");
            Assert.assertEquals(i, 10);
        }
    }

    @AfterSuite
    public void afterTest() {
        System.out.println("after test");
    }

    private int i;
}
