package pattern.adapter.case1;

/**
 * Created by Johnny on 2018/3/18.
 */
public class InterfaceAdapter {
    public static void main(String[] args) {
        TargetInterface source1 = new Source1();
        TargetInterface source2 = new Source2();
        source1.method1();
        source1.method2();
        source2.method1();
        source2.method2();
    }
}