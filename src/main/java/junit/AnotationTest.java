package junit;

import static org.junit.Assert.assertEquals; 

import org.junit.Ignore;
import org.junit.Test;

public class AnotationTest {
	@Test(expected=ArithmeticException.class)
	public void testDivide() {
//		assertEquals("除法有问题",3, new Calculate().divide(6, 0)); //将除数设置为0
		assertEquals("除法有问题",3, 6/0); //将除数设置为0
	}
	
	@Test(timeout=5)
	public void testWhile() {
		while(true) {
			System.out.println("run forever...");  //一个死循环
		}
	}
	
	@Test(timeout=10)
	public void testReadFile(){
		try {
			Thread.sleep(2000);       //模拟读文件操作
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Ignore("...")
	@Test
	public void testIgnore() {
		System.out.println("会运行吗？");  
	}

}
