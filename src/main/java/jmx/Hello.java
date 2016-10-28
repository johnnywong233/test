package jmx;

public class Hello implements HelloMBean {
	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void printHello() {
		System.out.println("Hello, Johnny " + name);		
	}

	@Override
	public void PrintHello(String whoName) {
		System.out.println("Hello, Johnny " + whoName);
	}
}
