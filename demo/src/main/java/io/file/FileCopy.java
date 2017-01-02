package io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileCopy {
    public static void main(String[] args) throws IOException {
        String source = "D:\\Java_ex\\test\\demo\\src\\test\\resources\\velocity.properties";
        String target = "D:\\Java_ex\\test\\demo\\src\\main\\resources\\velocity.properties";
        copyFile(source, target);
    }

    private static void copyFile(String sourceFileName, String targetFileName)
            throws IOException {
        File resourceFile = new File(sourceFileName);
        File targetFile = new File(targetFileName);

        if (!resourceFile.exists())
            abort("source file does not exist: " + sourceFileName);
        if (!resourceFile.isFile())
            abort("source is not a file" + sourceFileName);
        if (!resourceFile.canRead())
            abort("source " + sourceFileName + "does not have read permission");

        if (targetFile.isDirectory())
            targetFile = new File(targetFile, resourceFile.getName());

        if (targetFile.exists()) {
            if (!targetFile.canWrite())
                abort("target does not have write permission " + targetFileName);

            System.out.print("target is already exist, overwrite or not " + targetFile.getName() + "? (Y/N): ");
            System.out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    System.in));
            String response = in.readLine();

            if (!response.equals("Y") && !response.equals("y"))
                abort("target has already existed, and choose not overwrite..");
        } else {
            String parent = targetFile.getParent();
            if (parent == null)
                parent = System.getProperty("user.dir");
            File directory = new File(parent);
            if (!directory.exists())
                abort("parent " + parent + "does not exist");
            if (directory.isFile())
                abort("parent " + parent + "is not a file");
            if (!directory.canWrite())
                abort("parent " + parent + "does not have write permission");
        }

        FileInputStream resource = null;
        FileOutputStream target = null;
        try {
            resource = new FileInputStream(resourceFile);
            target = new FileOutputStream(targetFile);
            byte[] buffer = new byte[4096];
            int byteNum;

            while ((byteNum = resource.read(buffer)) != -1)
                target.write(buffer, 0, byteNum);
            System.out.print("succeed");
        } finally {
            if (resource != null)
                try {
                    resource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (target != null)
                try {
                    target.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private static void abort(String msg) throws IOException {
        throw new IOException("error occurred while copying, due to: " + msg);
    }
}
