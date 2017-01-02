package io.file;

import java.io.RandomAccessFile;

public class AppendText {
    public static void main(String[] args) {
        try {
            RandomAccessFile rf1 = new RandomAccessFile("C:\\Users\\wajian\\Documents\\Test\\user.txt", "rw");
            for (int i = 0; i < 10; i++) {
                rf1.writeBytes("this is line" + i + "\n");
            }
            rf1.close();
            int i = 0;
            String record;
            RandomAccessFile rf2 = new RandomAccessFile("C:\\Users\\wajian\\Documents\\Test\\user.txt", "rw");
            rf2.seek(rf2.length());
            rf2.writeBytes("append line" + "\n");
            rf2.close();
            RandomAccessFile rf3 = new RandomAccessFile("C:\\Users\\wajian\\Documents\\Test\\user.txt", "r");
            while ((record = rf3.readLine()) != null) {
                i++;
                System.out.println("Value " + i + ":" + record);
            }
            rf3.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
