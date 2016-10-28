package testng;

import org.testng.IAnnotationTransformer2;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by wajian on 2016/10/9.
 */
public class DataProviderAnnotationTransformerListener implements IAnnotationTransformer2 {
    @Override
    public void transform(IConfigurationAnnotation iConfigurationAnnotation, Class aClass, Constructor constructor, Method method) {
    }

    @Override
    public void transform(IDataProviderAnnotation annotation, Method method) {
        if (annotation.getName().equals("largeDataSet")) {
            System.out.println("Large data set, run parallely");
            annotation.setParallel(true);
        }
    }

    @Override
    public void transform(IFactoryAnnotation iFactoryAnnotation, Method method) {
    }

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
    }
}
