package guice.demo2;

import com.google.inject.ImplementedBy;

/**
 * Created by johnny on 2016/9/28.
 * interface define
 */
@ImplementedBy(MyServiceImpl.class)
public interface MyService {
    void myMethod();
}