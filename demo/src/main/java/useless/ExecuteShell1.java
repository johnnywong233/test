package useless;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by johnny on 2016/8/16.
 */
public class ExecuteShell1 {
    /**
     * <a href="http://www.jb51.net/article/44211.htm">...</a>
     * run "mkdir johnny"
     * 尝试用这种方法创建通过ssh登录到远程机器，遇到两个问题：
     * 1）如果希望没有人机对话方式，则需要使用命令sshpass -p password ssh user@targetIP 'command'
     * 3) 很多命令不能运行，原因还不清楚，最好改用Ganymed SSH-2库或者其他类似Java库。
     */
    public static void main(String[] args) {
        try {
            CommandHelper.DEFAULT_TIMEOUT = Integer.parseInt("20");
            CommandResult result = CommandHelper.exec("mkdir johnny");
            System.out.println("Output:" + result.getOutput());
            System.out.println("Error:" + result.getError());
        } catch (IOException ex) {
            System.out.println("IOException:" + ex.getLocalizedMessage());
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException:" + ex.getLocalizedMessage());
        }
    }
}

/**
 * Command Result
 */
@Data
class CommandResult {
    public static final int EXIT_VALUE_TIMEOUT = -1;
    private String output;
    private int exitValue;
    private String error;
}

/**
 * Command Helper
 */
class CommandHelper {
    private static final int DEFAULT_INTERVAL = 1000;
    //default time out, in mill seconds
    static int DEFAULT_TIMEOUT;
    private static long START;

    public static CommandResult exec(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        CommandResult commandResult = wait(process);
        process.destroy();
        return commandResult;
    }

    private static boolean isOverTime() {
        return System.currentTimeMillis() - START >= DEFAULT_TIMEOUT;
    }

    private static CommandResult wait(Process process) throws InterruptedException, IOException {
        BufferedReader errorStreamReader = null;
        BufferedReader inputStreamReader = null;
        try {
            errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //timeout control
            START = System.currentTimeMillis();
            boolean isFinished = false;
            for (; ; ) {
                if (isOverTime()) {
                    CommandResult result = new CommandResult();
                    result.setExitValue(CommandResult.EXIT_VALUE_TIMEOUT);
                    result.setOutput("Command process timeout");
                    return result;
                }
                if (isFinished) {
                    CommandResult result = new CommandResult();
                    result.setExitValue(process.waitFor());
                    //parse error info
                    if (errorStreamReader.ready()) {
                        StringBuilder buffer = new StringBuilder();
                        String line;
                        while ((line = errorStreamReader.readLine()) != null) {
                            buffer.append(line);
                        }
                        result.setError(buffer.toString());
                    }
                    //parse info
                    if (inputStreamReader.ready()) {
                        StringBuilder buffer = new StringBuilder();
                        String line;
                        while ((line = inputStreamReader.readLine()) != null) {
                            buffer.append(line);
                        }
                        result.setOutput(buffer.toString());
                    }
                    return result;
                }
                try {
                    isFinished = true;
                    process.exitValue();
                } catch (IllegalThreadStateException e) {
                    // process hasn't finished yet
                    isFinished = false;
                    Thread.sleep(DEFAULT_INTERVAL);
                }
            }
        } finally {
            if (errorStreamReader != null) {
                try {
                    errorStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}