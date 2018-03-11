package com.johnny.test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TestJarFile {
    public static void main(String[] args)
            throws IOException {
        JarFile jar = new JarFile("C:/Users/Think/Desktop/x.jar");
        String packageName = "com.johnny.service.handler.finder.impl";
        packageName = packageName.replaceAll("\\.", "/");

        Enumeration<JarEntry> e = jar.entries();
        while (e.hasMoreElements()) {
            JarEntry jarEntry = e.nextElement();
            String entryName = jarEntry.getName();
            if ((entryName.contains(packageName)) && (entryName.endsWith(".class"))) {
                System.out.println("name:" + entryName);
            }
        }
        jar.close();
    }
}
