package grammar.cglib;

/**
 * Author: Johnny Date: 2017/4/6 Time: 19:24
 */
class HelloImpl implements Hello {
	@Override
	public String sayHello(String name) {
		String s = "Hello, " + name;
		System.out.println(this.getClass().getName() + "->" + s);
		return s;
	}
}
