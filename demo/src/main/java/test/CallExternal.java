package test;

import java.io.IOException;

public class CallExternal {
    //java call external exe program
    //http://gundumw100.iteye.com/blog/438696
    public static void main(String[] args) throws IOException, InterruptedException {
        call1();
        call2();
        call3();
        call4();
    }

    private static void call1() throws IOException {
        Runtime.getRuntime().exec("notepad.exe");
    }

    private static void call2() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String[] commandArgs = {"notepad.exe", "c:/boot.ini"};
        runtime.exec(commandArgs);
    }

    private static void call3() throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        String[] commandArgs = {"notepad.exe", "c:/boot.ini"};
        Process process = runtime.exec(commandArgs);
        int exitCode = process.waitFor();//wait for the program to finish itself
        System.out.println("finish:" + exitCode);
    }

    //ProcessBuilder can realise similar function
    private static void call4() throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        String[] commandArgs = {"notepad.exe", "c:/boot.ini"};
        final Process process = runtime.exec(commandArgs);
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            process.destroy();//wait for 5s to abort program
        }).start();
        int exitCode = process.waitFor();
        System.out.println("finish:" + exitCode);
    }
}
