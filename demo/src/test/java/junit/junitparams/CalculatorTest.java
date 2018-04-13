package junit.junitparams;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Johnny on 2018/4/13.
 * http://wanghongxu.iteye.com/blog/2222975
 * 使用 Junit4 API 实现参数化校验
 */
@RunWith(Parameterized.class)
public class CalculatorTest {
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{{0, 0, 0}, {1, 1, 2}, {2, 1, 3}});
    }

    private int testOpt1;
    private int testOpt2;
    private int testResult;
    private Calculator calculator;

    public CalculatorTest(int testOpt1, int testOpt2, int testResult) {
        this.testOpt1 = testOpt1;
        this.testOpt2 = testOpt2;
        this.testResult = testResult;
    }

    @Before
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testAdd() {
        int actualResult = calculator.add(testOpt1, testOpt2);
        assertEquals(testResult, actualResult);
    }

    public static class Calculator {
        int add(int arg1, int arg2) {
            return arg1 + arg2;
        }
    }
}
