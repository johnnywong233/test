package grammar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class RuntimeDemo {
    /*
     * from 261 java problems PDF book 8.23, 8.24
     */
    public static void main(String[] args) throws IOException, InterruptedException {

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

        try {
            Runtime.getRuntime().exec("cmd /c \"C:\\Users\\wajian\\Documents\\Test\\test  tt.docx\"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Process p = Runtime.getRuntime().exec("ping www.163.com");
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while (true) {
            String s = br.readLine();
            if (s == null) {
                break;
            }
            System.out.println(s);
        }
        br.close();
        p.waitFor();
        if (p.exitValue() == 0) {
            System.out.println("success!");
        }
        System.out.println("exit code:" + p.exitValue());
    }

}
