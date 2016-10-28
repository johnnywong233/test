package testng;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Created by wajian on 2016/10/9.
 */
public class SuiteListenerExample {
    @Parameters("ui")
    @BeforeSuite
    public void beforeSuite(String parm) {
        System.out.println("before suite, ui value: " + parm);
    }

    @Test
    public void t() {
        System.out.println("test method");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("after suite");
    }
}
