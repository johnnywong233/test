package junit;

import static org.junit.Assert.assertEquals; 

import org.junit.Ignore;
import org.junit.Test;

public class AnotationTest {
	@Test(expected=ArithmeticException.class)
	public void testDivide() {
//		assertEquals("����������",3, new Calculate().divide(6, 0)); //����������Ϊ0
		Assert.assertEquals("����������",3, 6/0); //����������Ϊ0
	}
	
	@Test(timeout=5)
	public void testWhile() {
		while(true) {
			System.out.println("run forever...");  //һ����ѭ��
		}
	}
	
	@Test(timeout=10)
	public void testReadFile(){
		try {
			Thread.sleep(2000);       //ģ����ļ�����
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Ignore("...")
	@Test
	public void testIgnore() {
		System.out.println("��������");  
	}

}
