package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class CopyFilesUtil {
    //https://biezhi.me/article/java-copy-file.html
    public static void main(String[] args) throws InterruptedException,
            IOException {

        //C:\Users\wajian\Documents\Test
        File source = new File("C:\\Users\\wajian\\Documents\\Test\\sourcefile1.txt");
        File dest = new File("C:\\Users\\wajian\\Documents\\Test\\destfile1.txt");

        // copy file using FileStreams
        long start = System.nanoTime();
        long end;
        copyFileUsingFileStreams(source, dest);
        System.out.println("Time taken by FileStreams Copy = "
                + (System.nanoTime() - start));

        // copy files using java.nio.FileChannel
        source = new File("C:\\Users\\wajian\\Documents\\Test\\sourcefile2.txt");
        dest = new File("C:\\Users\\wajian\\Documents\\Test\\destfile2.txt");
        start = System.nanoTime();
        copyFileUsingFileChannels(source, dest);
        end = System.nanoTime();
        System.out.println("Time taken by FileChannels Copy = " + (end - start));

        // copy file using Java 7 Files class
        source = new File("C:\\Users\\wajian\\Documents\\Test\\sourcefile3.txt");
        dest = new File("C:\\Users\\wajian\\Documents\\Test\\destfile3.txt");
        start = System.nanoTime();
        copyFileUsingJdkFiles(source, dest);
        end = System.nanoTime();
        System.out.println("Time taken by Java7 Files Copy = " + (end - start));

        // copy files using apache commons io
        source = new File("C:\\Users\\wajian\\Documents\\Test\\sourcefile4.txt");
        dest = new File("C:\\Users\\wajian\\Documents\\Test\\destfile4.txt");
        start = System.nanoTime();
        copyFileUsingApacheCommonsIO(source, dest);
        end = System.nanoTime();
        System.out.println("Time taken by Apache Commons IO Copy = "
                + (end - start));

    }

    // method1
    public static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
        }
    }

    //method2
    @SuppressWarnings("resource")
    public static void copyFileUsingFileChannels(File source, File dest)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            if (inputChannel != null) {
                inputChannel.close();
            }
            if (outputChannel != null) {
                outputChannel.close();
            }
        }
    }

    // FileAlreadyExistsException: destfile3.txt
    // method4
    public static void copyFileUsingJdkFiles(File source, File dest)
            throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    //method3
    public static void copyFileUsingApacheCommonsIO(File source, File dest)
            throws IOException {
        FileUtils.copyFile(source, dest);
    }

}
