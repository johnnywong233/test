package cache.demo1;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * Author: Johnny
 * Date: 2017/9/3
 * Time: 16:52
 * test for fibonacci guava caching
 */
@Slf4j
public class FibonacciGuavaCacheTest {

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            log.info("f(" + i + ") = " + fibonacciCache.getUnchecked(i));
        }
    }

    private final LoadingCache<Integer, Integer> fibonacciCache = CacheBuilder.newBuilder()
            .maximumSize(2)
            .build(new CacheLoader<>() {
                @Override
                public Integer load(@NotNull Integer i) {
                    if (i == 0) {
                        return i;
                    }

                    if (i == 1) {
                        return 1;
                    }
                    return fibonacciCache.getUnchecked(i - 2) + fibonacciCache.getUnchecked(i - 1);
                }
            });


}
