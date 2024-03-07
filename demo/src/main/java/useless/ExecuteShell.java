package useless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExecuteShell {
    //java execute shell script
    //too many try-catch blocks here, changed with passive throws
    public static void main(String[] args) {
        //call system exe
//        Runtime.getRuntime().exec(System.getenv("windir") + File.separator + "system32" + File.separator + "shutdown.exe -s -f");

        Process process;
        List<String> processList = new ArrayList<>();
        try {
            //runnable command: ls, pwd, ps
            //Not working: ps -aux
            process = Runtime.getRuntime().exec("ps"); //ps -aux
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        processList.forEach(System.out::println);
    }
}
