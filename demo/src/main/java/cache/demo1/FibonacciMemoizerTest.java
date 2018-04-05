package cache.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Author: Johnny
 * Date: 2017/9/3
 * Time: 16:54
 */
public class FibonacciMemoizerTest {

    @Autowired
    private FibonacciService fibonacciService;

    @Test
    public void test_fibonacci() {
        assertEquals(55, fibonacciService.compute(10));
    }
}
