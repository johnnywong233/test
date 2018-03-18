package pattern.adapter.case1;

/**
 * Created by Johnny on 2018/3/18.
 * 类适配器
 */
public class ClassAdapter {
    public static void main(String[] args) {
        TargetInterface target = new Adapter();
        target.method1();
        target.method2();
    }
}