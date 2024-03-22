package cache.demo1;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Author: Johnny
 * Date: 2017/9/3
 * Time: 16:55
 */
@Slf4j
@Component
public class FibonacciServiceImpl {
    @Resource
    private ApplicationContext applicationContext;

    private FibonacciService fibonacciService;

    @PostConstruct
    private void init() {
        fibonacciService = applicationContext.getBean(FibonacciService.class);
    }

    @Memoize
    public int compute(int i) {
        log.info("Calculate fibonacci for number {}", i);
        if (i == 0 || i == 1) {
            return i;
        }
        return fibonacciService.compute(i - 2) + fibonacciService.compute(i - 1);
    }
}
