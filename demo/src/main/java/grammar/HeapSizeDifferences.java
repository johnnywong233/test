package grammar;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wajian on 2016/10/7.
 *
 */
public class HeapSizeDifferences {
    private static Collection<Object> objects = new ArrayList<>();
    private static long lastMaxMemory = 0;
    //http://www.importnew.com/15934.html
    public static void main(String[] args) {
        try {
            List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            System.out.println("Running with: " + inputArguments);
            while (true) {
                printMaxMemory();
                consumeSpace();
            }
        } catch (OutOfMemoryError e) {
            freeSpace();
            printMaxMemory();
        }
    }

    private static void printMaxMemory() {
        long currentMaxMemory = Runtime.getRuntime().maxMemory();
        if (currentMaxMemory != lastMaxMemory) {
            lastMaxMemory = currentMaxMemory;
            System.out.format("Runtime.getRuntime().maxMemory(): %,dK.%n", currentMaxMemory / 1024);
        }
    }

    private static void consumeSpace() {
        objects.add(new int[1_000_000]);
    }

    private static void freeSpace() {
        objects.clear();
    }
}
