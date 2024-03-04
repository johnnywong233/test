package io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by johnny on 2016/8/30.
 * demo of pipe
 */
public class PipeDemo {
    //http://ifeve.com/java-io-%E7%AE%A1%E9%81%93/
    public static void main(String[] args) throws IOException {
        final PipedOutputStream output = new PipedOutputStream();
        final PipedInputStream input = new PipedInputStream(output);

        Thread thread1 = new Thread(() -> {
            try {
                output.write("".getBytes());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                int data = input.read();
                while (data != -1){
                    System.out.println((char)data);
                    data = input.read();
                }
            } catch (Exception ex ) {
                ex.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        input.close();
    }
}
