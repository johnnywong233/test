package instrument;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * Created by Johnny on 2018/3/3.
 * 计算方法执行耗时，最土的方法是 System.currentTimeMillis()。
 * 现在使用 JDK 自带的 instrument API 来实现，当然仅仅是 jdk 还不够，需要一个字节码工具，本工程使用 javassist
 */
public class AopAgentTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        byte[] transformed = null;
        System.out.println("Transforming " + className);
        ClassPool pool;
        CtClass cl = null;
        try {
            pool = ClassPool.getDefault();

            cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));

            CtMethod aopMethod = pool.get("instrument.AopAgentTest").getDeclaredMethod("premain");
            System.out.println(aopMethod.getLongName());

//            CodeConverter convert = new CodeConverter();

            if (!cl.isInterface()) {
                CtMethod[] methods = cl.getDeclaredMethods();
                for (CtMethod method : methods) {
                    if (!method.isEmpty()) {
                        aopInsertMethod(method);
                    }
                }
                transformed = cl.toBytecode();
            }
        } catch (Exception e) {
            System.err.println("Could not instrument  " + className + ",  exception : " + e.getMessage());
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return transformed;
    }

    private void aopInsertMethod(CtMethod method) throws CannotCompileException {
        //situation 1:添加监控时间
        method.instrument(new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                m.replace("{ long stime = System.currentTimeMillis(); $_ = $proceed($$);System.out.println(\""
                        + m.getClassName() + "." + m.getMethodName() + " cost:\" + (System.currentTimeMillis() - stime) + \" ms\");}");
            }
        });
        //situation 2:在方法体前后语句
        method.insertBefore("System.out.println(\"enter method\");");
        method.insertAfter("System.out.println(\"leave method\");");
    }

}