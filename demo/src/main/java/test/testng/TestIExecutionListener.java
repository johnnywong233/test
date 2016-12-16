package test.testng;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Created by wajian on 2016/10/9.
 */
public class TestIExecutionListener {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("beforeSuite");
    }

    @Test
    //no desired effort if there is no XML file
    public void t() {
        System.out.println("test");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("afterSuite");
    }
}
