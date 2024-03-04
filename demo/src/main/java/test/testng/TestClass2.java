package test.testng;

import org.testng.annotations.Test;

/**
 * Created by johnny on 2016/10/9.
 */
public class TestClass2 {
	//both class1Method and TestClass1.class1Method error
	//TestClass2.class2Method() depends on nonexistent method class1Method
//    @Test(dependsOnMethods = {"TestClass1.class1Method"})
	
	@Test(dependsOnGroups={"TestClass1.class1Method"})
    public void class2Method() {
        System.out.println("class 2 method 1");
    }
}
