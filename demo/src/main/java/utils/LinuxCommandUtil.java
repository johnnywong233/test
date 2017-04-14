package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author: Johnny
 * Date: 2017/4/14
 * Time: 17:49
 */
public class LinuxCommandUtil {

    public static String executeCommand(String command) throws IOException, InterruptedException {
        StringBuilder output = new StringBuilder();
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }
        return output.toString();
    }

}
