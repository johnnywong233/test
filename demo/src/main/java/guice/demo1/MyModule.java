package guice.demo1;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

/**
 * Created by johnny on 2016/9/28.
 */
public class MyModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(MyService.class).to(MyServiceImpl.class).in(Scopes.SINGLETON);
        //运行时动态地将MyServiceImpl对象赋给MyService定义的对象，而且这个对象是单例的。
    }
}