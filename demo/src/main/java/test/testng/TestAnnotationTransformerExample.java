package test.testng;

import org.testng.annotations.Test;

/**
 * Created by johnny on 2016/10/9.
 */
public class TestAnnotationTransformerExample {
	//add a xml file, or there would be no desired output
    @Test
    public void t1(String param) {
        System.out.println("Method is t1, parameter is " + param);
    }

    @Test
    public void t2(String param) {
        System.out.println("Method is t2, parameter is " + param);
    }

    @Test
    public void t3() {
        System.out.println("Method is t3");
    }
}
