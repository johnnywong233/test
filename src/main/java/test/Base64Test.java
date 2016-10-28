package test;

import org.apache.commons.codec.binary.Base64;

public class Base64Test {
	public static void main(String[] args) {
		
		/*
		 * 本意是测试trim方法的效果和作用
		 */
		String str = "Test Base64";
		//报错，之前还用过这个方法，怎么回事？
//		String notTrim = Base64.encodeBase64String(str.getBytes());
		
		//
		byte[] notTrim = Base64.encodeBase64Chunked(str.getBytes());
		
		byte[] Trim = Base64.encodeBase64Chunked(str.getBytes());
		byte[] tt = Base64.encodeBase64(str.getBytes());
		
		System.out.println(notTrim);
		System.out.println(Trim);
		
	}
	

}
