package grammar.reflection.reflectasm;

import com.esotericsoftware.reflectasm.MethodAccess;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * Author: Johnny
 * Date: 2016/12/18
 * Time: 21:54
 */
public class ReflectAsmClient {
    private static final int SIZE = 100000000;
    public static void main(String[] args) throws Exception {
        testJdkReflect();
        testReflectAsm();
        testJdkReflect1();
        testReflectAsm1();
    }

    private static void testJdkReflect() throws Exception {
        SomeClass someObject = new SomeClass();
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < SIZE; j++) {
                Method method = SomeClass.class.getMethod("foo", String.class);
                method.invoke(someObject, "johnny");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
        System.out.println();
    }

    private static void testReflectAsm() {
        SomeClass someObject = new SomeClass();
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < SIZE; j++) {
                MethodAccess access = MethodAccess.get(SomeClass.class);
                access.invoke(someObject, "foo", "johnny");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
        System.out.println();
    }

    private static void testJdkReflect1() throws Exception {
        SomeClass someObject = new SomeClass();
        Method method = SomeClass.class.getMethod("foo", String.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < SIZE; j++) {
                method.invoke(someObject, "johnny");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
        System.out.println();
    }

    private static void testReflectAsm1() {
        SomeClass someObject = new SomeClass();
        MethodAccess access = MethodAccess.get(SomeClass.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < SIZE; j++) {
                access.invoke(someObject, "foo", "johnny");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
        System.out.println();
    }
}

@Data
class SomeClass {
    private String name;

    public void foo(String name) {
        this.setName(name);
    }
}