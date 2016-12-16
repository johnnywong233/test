package grammar;

public class OverloadingDemo {
	public static void main(String[] args) {
		OverloadingDemo demo = new OverloadingDemo();
		System.out.println(demo.out());
	}
	
	//overloading 
	public String out() {
		String string = "test";
		return string;
	}

	public String out(String string) {
		return string;
	}
	
	//contains abstract method, must be a abstract class
//	abstract String print();
	
}
