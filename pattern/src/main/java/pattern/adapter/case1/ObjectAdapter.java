package pattern.adapter.case1;

/**
 * Created by Johnny on 2018/3/18.
 */
public class ObjectAdapter {
    public static void main(String[] args) {
        Source source = new Source();
        Adapter1 adapter = new Adapter1(source);
        adapter.method1();
        adapter.method2();
    }
}