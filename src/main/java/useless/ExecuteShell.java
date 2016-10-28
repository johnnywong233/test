package useless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wajian on 2016/8/16.
 */
public class ExecuteShell {
	//java execute shell script
	//too many try-catch blocks here, changed with passive throws
    public static void main(String args[]) throws IOException, InterruptedException{
    	//add execute authority
    	//TODO
        String cmdstring = "chmod a+x test.sh";
        Process proc = Runtime.getRuntime().exec(cmdstring);
        //阻塞，直到上述命令执行完
        proc.waitFor();
        //can be other format shell script, such as *.ksh
        
        cmdstring = "bash test.sh";
//        cmdstring = "bash C:\\work\\test\\src\\test\\resources\\test.sh";
        proc = Runtime.getRuntime().exec(cmdstring);
        //note the following
        String ls_1;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        while ((ls_1 = bufferedReader.readLine()) != null);
        bufferedReader.close();
        proc.waitFor();
        
        //可执行程序的输出可能会比较多，而运行窗口的输出缓冲区有限，会造成waitFor一直阻塞。解决的办法是，利用Java提供
        //的Process类提供的getInputStream,getErrorStream方法让Java虚拟机截获被调用程序的标准输出、错误输出，
        //在waitfor()命令之前读掉输出缓冲区中的内容。
    }
}
