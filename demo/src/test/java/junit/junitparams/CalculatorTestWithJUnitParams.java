package junit.junitparams;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static junitparams.JUnitParamsRunner.$;

/**
 * Created by Johnny on 2018/4/13.
 * 两段代码对比显而易见：
 * 不用通过构造器传递测试参数，用在方法前注解参数内容即可（使用Parameters注解）
 * 测试用例可以指定任意的数据提供方法（使用Parameters里的method）
 */
@RunWith(JUnitParamsRunner.class)
public class CalculatorTestWithJUnitParams {
    private Calculator calculator;

    @Before
    public void setUp() {
        calculator = new Calculator();
    }

    private Object addTestData() {
        return new Object[]{
                new Object[]{0, 0, 0},
                new Object[]{1, 1, 2},
                new Object[]{2, 1, 3}
        };
    }

    private Object addTestData1() {
        return $(
                $(0, 0, 0),
                $(1, 1, 2),
                $(2, 1, 3)
        );
    }

    @Test
    @Parameters(method = "addTestData")
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
