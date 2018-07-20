package test.testng;

import org.testng.annotations.Test;

public class TestngFactoryTest {
    private int numberOfTimes;

    public TestngFactoryTest(int numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }

    private static int num;

    @Test
    public void testServer() {
        num ++;
        System.out.println("num " + num + "  m_numberOfTimes ：" + numberOfTimes);
    }
}