package junit.junitparams;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Johnny on 2018/4/13.
 */
@RunWith(JUnitParamsRunner.class)
public class CalculatorTestWithJUnitParams1 {
    private Calculator calculator;

    @Before
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    @Parameters({"0, 0, 0", "1, 1, 2", "2, 1, 3"})
    public void testAdd(int testOpt1, int testOpt2, int testResult) throws Exception {
        int actualResult = calculator.add(testOpt1, testOpt2);
        assertEquals(testResult, actualResult);
    }

    public static class Calculator {
        int add(int arg1, int arg2) {
            return arg1 + arg2;
        }
    }
}
