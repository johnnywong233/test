package utils.ratelimiter;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2018/8/8
 * Time: 22:04
 * token 生产者管理器
 */
public class TokenProducerManager {
    /**
     * 按照指定速率添加token
     *
     * @param key    指定请求ID
     * @param period 速率
     * @param limit  桶的大小
     */
    private static TokenBucket execute(String key, Long period, Integer limit) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("TokenProducerManager-%d").daemon(true).build());

        TokenBucket tokenBucket = TokenBucket.builder().key(key).limit(limit).build();
        executorService.scheduleAtFixedRate(new TokenProducer(tokenBucket), 0L, period, TimeUnit.SECONDS);
        return tokenBucket;
    }

    public static TokenBucket addTokenAtFixRate(String key, Long period, Integer limit) {
        return execute(key, period, limit);
    }
}
