package junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JunitFlowTest {
    @BeforeClass
    public static void setUpBeforeClass() {
        System.out.println("this is setUpBeforeClass()...");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println("this is tearDownAfterClass()...");
    }

    @Before
    public void setUp() {
        System.out.println("this is setUp()...");
    }

    @After
    public void tearDown() {
        System.out.println("this is tearDown()...");
    }

    @Test
    public void test() {
        System.out.println("this is test()...");
    }

    @Test
    public void test2() {
        System.out.println("this is test2()...");
    }

}
