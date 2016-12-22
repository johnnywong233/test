package java8;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/12/21
 * Time: 20:23
 */
public class Java8Cache {
	//TODO
	//http://ifeve.com/java8-local-caching/
    public static void main(String[] args) {
        int i = 30;
        long startTime = System.currentTimeMillis();
        System.out.println("f(" + i + ") = " + fibonacci(i));
        System.out.println("Time consumed " + (System.currentTimeMillis() - startTime));
        
        startTime = System.currentTimeMillis();
        System.out.println("f(" + i + ") = " + cacheFibonacci(i));
        System.out.println("Time consumed " + (System.currentTimeMillis() - startTime));

    }

    private static int fibonacci(int i) {
        if (i == 0)
            return i;
        if (i == 1)
            return 1;
        return fibonacci(i - 2) + fibonacci(i - 1);
    }

    private static int cacheFibonacci(int i) {
        Map<Integer, Integer> cache = new HashMap<>();
        if (i == 0)
            return i;
        if (i == 1)
            return 1;
        return cache.computeIfAbsent(i, (key) -> cacheFibonacci(i - 2) + cacheFibonacci(i - 1));
    }


}
