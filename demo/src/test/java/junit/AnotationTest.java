package junit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class AnotationTest {
    @Test(expected = ArithmeticException.class)
    public void testDivide() {
//		assertEquals("error",3, new Calculate().divide(6, 0));
        Assert.assertEquals("error", 3, 6 / 0);
    }

    @Test(timeout = 5)
    public void testWhile() {
        while (true) {
            System.out.println("run forever...");
        }
    }

    @Test(timeout = 10)
    public void testReadFile() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Ignore("...")
    @Test
    public void testIgnore() {
        System.out.println("test on @Ignore");
    }

}
