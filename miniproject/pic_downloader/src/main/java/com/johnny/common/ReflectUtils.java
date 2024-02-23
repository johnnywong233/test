package com.johnny.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflectUtils {
    private static Integer PARA_TYPE_INCLUDE = 0;
    private static Integer PARA_TYPE_EXCLUDE = 1;

    public static Map<String, Object> getFieldValues(Object obj)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        return getFieldValues(obj, null, new Object[0]);
    }

    private static Map<String, Object> getFieldValues(Object obj, Integer paraType, Object[] paras) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Set<String> fieldNameSet = new HashSet<>();
        if ((paraType != null) && (
                (Objects.equals(paraType, PARA_TYPE_INCLUDE)) || (Objects.equals(paraType, PARA_TYPE_EXCLUDE)))) {
            for (String name : paras[0].toString().split(",")) {
                fieldNameSet.add(name.trim());
            }
        }

        Map<String, Object> map = new HashMap<>();
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if ((methodName.startsWith("get")) || (methodName.startsWith("is"))) {
                Class<?> returnTypeClass = method.getReturnType();
                String fieldName;
                if ((returnTypeClass.equals(Boolean.TYPE)) || (returnTypeClass.equals(Boolean.class))) {
                    fieldName = methodName.substring(2, 3).toLowerCase() + methodName.substring(3);
                } else {
                    fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                }
                boolean flag = true;
                if (Objects.equals(paraType, PARA_TYPE_INCLUDE)) {
                    flag = fieldNameSet.contains(fieldName);
                }
                if (Objects.equals(paraType, PARA_TYPE_EXCLUDE)) {
                    flag = !fieldNameSet.contains(fieldName);
                }
                if (flag) {
                    map.put(fieldName, method.invoke(obj));
                }
            }
        }
        return map;
    }

    public static List<Class<?>> getClassWithPackage(String pkg) {
        try {
            String jarPath = URLDecoder.decode(ReflectUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath(), StandardCharsets.UTF_8);
            File file = new File(jarPath);
            if ((file.isFile()) && (file.getName().endsWith("jar"))) {
                JarFile jar = new JarFile(jarPath);
                return getClassWithPackageFromJar(jar, pkg);
            }
            return getClassWithPackageFromDir(pkg);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Class<?>> getClassWithPackageFromJar(JarFile jar, String pkg)
            throws IOException, ClassNotFoundException {
        String packageName = pkg.replaceAll("\\.", "/");
        List<Class<?>> list = new ArrayList<>();
        ClassLoader loader = ReflectUtils.class.getClassLoader();
        Enumeration<JarEntry> e = jar.entries();
        while (e.hasMoreElements()) {
            JarEntry jarEntry = e.nextElement();
            String entryName = jarEntry.getName();
            if ((entryName.contains(packageName)) && (entryName.endsWith(".class"))) {
                list.add(loader.loadClass(entryName.replace("/", ".").replace(".class", "")));
            }
        }
        return list;
    }

    private static List<Class<?>> getClassWithPackageFromDir(String pkg)
            throws UnsupportedEncodingException, ClassNotFoundException {
        List<Class<?>> list = new ArrayList<>();
        String jarPath = URLDecoder.decode(ReflectUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath(), StandardCharsets.UTF_8);
        File dir = new File(jarPath + "/" + pkg.replaceAll("\\.", "/"));
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".class"));
        if (files != null) {
            for (File f : files) {
                String className = f.getName().substring(0, f.getName().indexOf("."));
                Class<?> clazz = Class.forName(pkg + "." + className);
                list.add(clazz);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        List<Class<?>> list = getClassWithPackage("com.johnny.service.handler.finder.impl");
        if (list != null) {
            for (Class<?> cls : list) {
                System.out.println(cls);
            }
        }
    }
}
