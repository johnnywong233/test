package pattern.adapter.case1;

/**
 * Created by Johnny on 2018/3/18.
 */
public class Adapter extends Source implements TargetInterface {
    @Override
    public void method2() {
        System.out.println("this is the TargetInterface method!");
    }
}