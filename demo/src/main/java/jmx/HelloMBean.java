package jmx;

public interface HelloMBean {
	String getName();
	void setName(String name);
	void printHello();
	void PrintHello(String whoName);
}