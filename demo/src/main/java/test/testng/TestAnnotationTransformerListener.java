package test.testng;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by wajian on 2016/10/9.
 */
public class TestAnnotationTransformerListener implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        if ("t1".equals(testMethod.getName())) {
            System.out.println("set data provider for " + testMethod.getName());
            annotation.setDataProviderClass(DataProviderFactory.class);
            annotation.setDataProvider("getDp1");
        } else if ("t2".equals(testMethod.getName())) {
            System.out.println("set data provider for " + testMethod.getName());
            annotation.setDataProviderClass(DataProviderFactory.class);
            annotation.setDataProvider("getDp2");
        } else if ("t3".equals(testMethod.getName())) {
            System.out.println("Disable " + testMethod.getName());
            annotation.setEnabled(false);
        }
    }
}
