package grammar.delegate;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

/*
 * http://www.cnblogs.com/soojoe/archive/2012/04/12/2532304.html
 */

public class Delegator4Map extends Delegator {
    private Map originClass; //原始对象
    private Map proxyClass; //代理对象

    public Map getOrigin() {
        return originClass;
    }

    public Map getProxy() {
        return proxyClass;
    }

    public Delegator4Map(Map origin) {
        super(origin);
        originClass = origin;
        proxyClass = (Map) super.objProxy;
    }

    @Override
    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
        if ("size".equals(method.getName())) { //修改size处理逻辑
            Object res2 = -1;
            System.out.println("调用委托的方法");
            return res2;
        } else {
            System.out.println("调用原始的方法");
            return super.invoke(obj, method, args);
        }
    }

    public static void main(String[] args) throws IOException {
        Delegator4Map rtm = new Delegator4Map(new Hashtable<>());
        Map m = rtm.getProxy();
        m.size();
    }
}
