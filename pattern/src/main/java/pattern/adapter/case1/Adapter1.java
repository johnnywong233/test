package pattern.adapter.case1;

/**
 * Created by Johnny on 2018/3/18.
 */
public class Adapter1 implements TargetInterface {
    private Source source;

    public Adapter1(Source source) {
        super();
        this.source = source;
    }

    @Override
    public void method2() {
        System.out.println("this is the targetable method!");
    }

    @Override
    public void method1() {
        source.method1();
    }
}
