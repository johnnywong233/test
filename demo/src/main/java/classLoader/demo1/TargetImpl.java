package classLoader.demo1;

/**
 * Created by wajian on 2016/10/7.
 */
public class TargetImpl implements Target {
    @Override
    public String name() {
        return "this is to test dynamically load class";
    }
}
