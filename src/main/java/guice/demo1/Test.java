package guice.demo1;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by johnny on 2016/9/28.
 * test class
 */
public class Test {
    public static void main(String[] args) {
        MyModule module = new MyModule();// 定义注射规则
        Injector injector = Guice.createInjector(module);// 根据注射规则，生成注射者
        Client client = new Client();
        injector.injectMembers(client);// 注射者将需要注射的bean,按照规则,把client这个客户端进行注射
        client.myMethod();
    }
}