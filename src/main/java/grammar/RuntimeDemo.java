package grammar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class RuntimeDemo {
	/*
	 * 你必须知道的261个Java语言问题,08.23,08.24等
	 */
	public static void main(String args[]) throws IOException, InterruptedException{
		
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		System.out.println(RuntimeDemo.class.getClassLoader().getResource(""));
		System.out.println(ClassLoader.getSystemResource(""));
		System.out.println(RuntimeDemo.class.getResource(""));
		System.out.println(RuntimeDemo.class.getResource("/"));
		
		System.out.println(new File("").getAbsolutePath());
		System.out.println(System.getProperty("user.dir"));
		
		
		try {
			Runtime.getRuntime().exec("cmd.exe /c start C:\\Users\\wajian\\Documents\\Test\\testtt.docx");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//如果文件名包含空格！其他任意类型的文件都可以打开，前提是安装有打开该文件类型的应用程序
		try {
			Runtime.getRuntime().exec("cmd /c \"C:\\Users\\wajian\\Documents\\Test\\test  tt.docx\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Process p = Runtime.getRuntime().exec("ping www.163.com");
		//获取响应信息
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while(true){
			String s = br.readLine();
			//读取完毕，退出循环
			if(s == null) break;
			System.out.println(s);
		}
		br.close();
		//等待Process对象表示的进程终止
		p.waitFor();
		//标志为0表示正常结束
		if(p.exitValue() == 0) {
			System.out.println("success!");
		}
		System.out.println("exit code:" + p.exitValue());
		
		
		
	}

}
