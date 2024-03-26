package grammar.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
 * http://blog.csdn.net/xieyuooo/article/details/7624146#
 */
//定义了一个接口
interface Hello {
    String getInfos1();

    String getInfos2();

    void setInfo(String infos1, String infos2);

    void display();
}

//定义它的实现类
class HelloImplements implements Hello {

    private volatile String infos1;

    private volatile String infos2;

    @Override
    public String getInfos1() {
        return infos1;
    }

    @Override
    public String getInfos2() {
        return infos2;
    }

    @Override
    public void setInfo(String infos1, String infos2) {
        this.infos1 = infos1;
        this.infos2 = infos2;
    }

    @Override
    public void display() {
        System.out.println("\t\t" + infos1 + "\t" + infos2);
    }
}

//定义AOP的Agent
class AOPFactory implements InvocationHandler {

    private final Object proxyed;

    AOPFactory(Object proxyed) {
        this.proxyed = proxyed;
    }

    private void printInfo(String info, Object... args) {
        System.out.println(info);
        if (args == null) {
            System.out.println("\t空值。");
        } else {
            for (Object obj : args) {
                System.out.println(obj);
            }
        }
    }

    @Override
    public Object invoke(Object proxyed, Method method, Object[] args) throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        System.out.println("\n\n====>调用方法名：" + method.getName());
        Class<?>[] variables = method.getParameterTypes();
        for (Class<?> typevariables : variables) {
            System.out.println("=============>" + typevariables.getName());
        }
        printInfo("传入的参数为：", args);
        Object result = method.invoke(this.proxyed, args);
        printInfo("返回的参数为：", result);
        printInfo("返回值类型为：", method.getReturnType());
        return result;
    }
}

//测试调用类
public class DynamicProxy {

    private static Object getBean(String className) throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        Object obj = Class.forName(className).getDeclaredConstructor().newInstance();
        InvocationHandler handler = new AOPFactory(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
                .getClass().getInterfaces(), handler);
    }

    public static void main(String[] args) {
        try {
            Hello hello = (Hello) getBean("dynamic.HelloImplements");
            hello.setInfo("xieyu1", "xieyu2");
            hello.getInfos1();
            hello.getInfos2();
            hello.display();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}