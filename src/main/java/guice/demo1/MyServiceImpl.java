package guice.demo1;

/**
 * Created by johnny on 2016/9/28.
 * implement class
 */
public class MyServiceImpl implements MyService{
    @Override
    public void myMethod() {
        System.out.println("so, this is your first guice demo!");
    }
}