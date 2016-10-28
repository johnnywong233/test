package testng;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by wajian on 2016/10/9.
 */
public class TestClass1 {
	@BeforeClass
	  public static void verifyConfig() {
	    //verify some test config parameters, Usually just throw exceptions, assert statements will work
	  }
	
    @Test(groups={"TestClass1.class1Method"})
    public void class1Method() {
        System.out.println("class 1 method 1");
    }
}
