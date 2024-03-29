package cache.demo1;

import jakarta.annotation.Resource;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Author: Johnny
 * Date: 2017/9/3
 * Time: 16:54
 */
public class FibonacciMemoizerTest {

    @Resource
    private FibonacciService fibonacciService;

    @Test
    public void testFibonacci() {
        assertEquals(55, fibonacciService.compute(10));
    }
}
