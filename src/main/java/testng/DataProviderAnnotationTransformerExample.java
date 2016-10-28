package testng;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Created by wajian on 2016/10/9.
 */
@Listeners(value=DataProviderAnnotationTransformerListener.class)
public class DataProviderAnnotationTransformerExample {
    @Test(dataProvider="largeDataSet", dataProviderClass=DataProviderFactory.class)
    public void largeDataTest(String param) {
        System.out.println("Method is t3, parameter is " + param + " threadId: " + Thread.currentThread().getId());
    }
}

/* even though there is a XML file to config the listener, if you run this test class as TestNG, the output will be:
 * and the hot-key shift + ALT + X, N
Method is t3, parameter is Large threadId: 1
Method is t3, parameter is Data threadId: 1
Method is t3, parameter is Set threadId: 1
PASSED: largeDataTest("Large")
PASSED: largeDataTest("Data")
PASSED: largeDataTest("Set")
*/
//note that run as TestNG test will not produce the same output as run as TestNG suite.
//and there is run configuration