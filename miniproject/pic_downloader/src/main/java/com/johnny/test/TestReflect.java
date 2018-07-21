package com.johnny.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TestReflect {
    private static List<String[]> getJarMethod(String jarFile)
            throws Exception {
        String normalMethod = "waitequalsnotifynotifyAlltoStringhashCodegetClass";
        List<String[]> list = new ArrayList<>();
        try {
            JarFile jar = new JarFile(jarFile);
            Enumeration<JarEntry> e = jar.entries();

            while (e.hasMoreElements()) {
                JarEntry entry = e.nextElement();

                if (!entry.getName().contains("META-INF")) {
                    String sName = entry.getName();
                    String[] subStr = sName.split("/");
                    String pName = "";
                    for (int i = 0; i < subStr.length - 1; i++) {
                        if (i > 0) {
                            pName = pName + "/" + subStr[i];
                        } else {
                            pName = subStr[i];
                        }
                    }
                    if (!sName.contains(".class")) {
                        sName = sName.substring(0, sName.length() - 1);
                    } else {
                        URL url1 = new URL("file:E:\\Java\\tool\\settings.jar");
                        URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url1}, Thread.currentThread().getContextClassLoader());
                        String ppName = sName.replace("/", ".").replace(".class", "");
                        System.out.println("ppName:" + ppName);
                        Class<?> myClass = myClassLoader.loadClass(ppName);

                        Method[] method = myClass.getMethods();
                        for (Method aM : method) {
                            String sm = aM.getName();
                            if (!normalMethod.contains(sm)) {
                                String[] c = {sm, sName};
                                list.add(c);
                            }
                        }
                        myClassLoader.close();
                    }
                    String[] b = {sName, pName};
                    list.add(b);
                }
            }
            jar.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        List<String[]> list = getJarMethod("E:\\Java\\tool\\settings.jar");
        for (String[] arr : list) {
            System.out.println(Arrays.toString(arr));
        }
    }
}