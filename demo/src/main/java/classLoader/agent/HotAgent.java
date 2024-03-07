package classLoader.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Set;
import java.util.Timer;
import java.util.TreeSet;

/**
 * Created by johnny on 2016/10/2.
 */
public class HotAgent {
    protected static Set<String> clsnames = new TreeSet<>();

    public static void premain(String agentArgs, Instrumentation inst) {
        ClassFileTransformer transformer = new ClassTransform(inst);
        inst.addTransformer(transformer);
        System.out.println("是否支持类的重定义：" + inst.isRedefineClassesSupported());
        Timer timer = new Timer();
        timer.schedule(new ReloadTask(inst), 2000, 2000);
    }

}