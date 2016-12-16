package utils;

import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnny on 2016/9/15.
 * use reflect to get class info
 */
public class ClassInfoUtil {

    private static String getModifier(int modifier) {
        String result = "";
        switch (modifier) {
            case Modifier.PRIVATE:
                result = "private";
                break;
            case Modifier.PUBLIC:
                result = "public";
                break;
            case Modifier.PROTECTED:
                result = "protected";
                break;
            case Modifier.ABSTRACT:
                result = "abstract";
                break;
            case Modifier.FINAL:
                result = "final";
                break;
            case Modifier.NATIVE:
                result = "native";
                break;
            case Modifier.STATIC:
                result = "static";
                break;
            case Modifier.SYNCHRONIZED:
                result = "synchronized";
                break;
            case Modifier.STRICT:
                result = "strict";
                break;
            case Modifier.TRANSIENT:
                result = "transient";
                break;
            case Modifier.VOLATILE:
                result = "volatile";
                break;
            case Modifier.INTERFACE:
                result = "interface";
                break;
            default:
                break;
        }
        return result;
    }

    private static void printClassDefinition(Class<?> clz) {
        String clzModifier = getModifier(clz.getModifiers());
        if (clzModifier != null && !clzModifier.equals("")) {
            clzModifier += "  ";
        }
        String superClz = "";
        if (clz.getSuperclass() != null) {
            superClz = clz.getSuperclass().getName();
            if (superClz != null && !superClz.equals("")) {
                superClz = " extends " + superClz;
            }
        }

        Class<?>[] interfaces = clz.getInterfaces();
        String inters = "";
        for (int i = 0; i < interfaces.length; i++) {
            if (i == 0) {
                inters += " implements ";
            }
            inters += interfaces[i].getName();
        }

        System.out.println(clzModifier + clz.getName() + " " + superClz + " " + inters);
        System.out.println("{");
        System.out.println("fields:");
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            String modifier = getModifier(field.getModifiers());
            if (modifier != null && !modifier.equals("")) {
                modifier = modifier + " ";
            }
            String fieldName = field.getName();
            String fieldType = field.getType().getName();
            System.out.println("    " + modifier + fieldType + " " + fieldName + ";");
        }
        System.out.println("methods:");
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            String modifier = getModifier(method.getModifiers());
            if (modifier != null && !modifier.equals("")) {
                modifier = modifier + " ";
            }
            String methodName = method.getName();
            Class<?> returnClz = method.getReturnType();
            String returnType = returnClz.getName();
            Class<?>[] clzs = method.getParameterTypes();
            String paraList = "(";
            for (int j = 0; j < clzs.length; j++) {
                paraList += clzs[j].getName();
                if (j != clzs.length - 1) {
                    paraList += ", ";
                }
            }
            paraList += ")";
            clzs = method.getExceptionTypes();
            String exceptions = "";
            for (int j = 0; j < clzs.length; j++) {
                if (j == 0) {
                    exceptions += "throws ";
                }
                exceptions += clzs[j].getName();
                if (j != clzs.length - 1) {
                    exceptions += ", ";
                }
            }
            exceptions += ";";
            String methodPrototype = modifier + returnType + " " + methodName + paraList + exceptions;
            System.out.println("    " + methodPrototype);
        }
        System.out.println("}");
    }

    //http://www.phpxs.com/code/1001557/
    @Test
    public void testClassInfoUtil() throws Exception {
        printClassDefinition(List.class);
        printClassDefinition(Proxy.class);
        printClassDefinition(ArrayList.class);
    }
}
