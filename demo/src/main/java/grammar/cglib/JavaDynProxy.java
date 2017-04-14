package grammar.cglib;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: Johnny Date: 2017/4/6 Time: 19:37
 */
public class JavaDynProxy implements InvocationHandler {

	private Object target;

	private Object getProxyInstance(Object target) {
		this.target = target;
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result;
		System.out.println("before target method...");
		result = method.invoke(target, args);
		System.out.println("after target method...");
		return result;
	}

	public static void main(String[] args) {
		JavaDynProxy proxy = new JavaDynProxy();
		Hello hello = (Hello) proxy.getProxyInstance(new HelloImpl());
		String s = hello.sayHello("Leon");
		System.out.println(s);

		UserDao userDao = (UserDao) proxy.getProxyInstance(new UserDaoImpl());
		userDao.login("Leon", "1234");
		System.out.println(userDao.getClass().getName());
	}
}
