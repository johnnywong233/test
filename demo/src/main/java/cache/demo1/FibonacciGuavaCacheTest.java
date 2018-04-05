package cache.demo1;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Author: Johnny
 * Date: 2017/9/3
 * Time: 16:52
 * test for fibonacci guava caching
 */
public class FibonacciGuavaCacheTest {
    private Logger LOGGER = LoggerFactory.getLogger(FibonacciGuavaCacheTest.class);

    private LoadingCache<Integer, Integer> fibonacciCache = CacheBuilder.newBuilder()
            .maximumSize(2)
            .build(new CacheLoader<Integer, Integer>() {
                public Integer load(Integer i) {
                    if (i == 0)
                        return i;

                    if (i == 1)
                        return 1;
                    return fibonacciCache.getUnchecked(i - 2) + fibonacciCache.getUnchecked(i - 1);
                }
            });

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            LOGGER.info("f(" + i + ") = " + fibonacciCache.getUnchecked(i));
        }
    }
}
