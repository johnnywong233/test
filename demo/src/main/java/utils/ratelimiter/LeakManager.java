package utils.ratelimiter;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: Johnny
 * Date: 2018/8/9
 * Time: 10:50
 */
public class LeakManager {
    private static LeakBucket<String> execute(String key, Long period, Integer limit) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("TokenProducerManager-%d").daemon(true).build());
        LeakBucket<String> leakBucket = new LeakBucket<>();
        leakBucket.setLimit(limit);
        leakBucket.setKey(key);
        executorService.scheduleAtFixedRate(new FlowTask(leakBucket), 0L, period, TimeUnit.SECONDS);
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.scheduleAtFixedRate(new LeakTask(leakBucket), 0L, period, TimeUnit.SECONDS);
        return leakBucket;
    }

    /**
     * 按照指定速率添加token
     *
     * @param key    指定请求ID
     * @param period 速率
     * @param limit  桶的大小
     */
    private static LeakBucket<String> leakRequestAtFixRate(String key, Long period, Integer limit) {
        return execute(key, period, limit);
    }

    public static void main(String[] args) throws InterruptedException {
        LeakBucket<String> leakBucket = LeakManager.leakRequestAtFixRate("request a", 1L, 2000);
        Thread.sleep(6000L);
        for (int i = 0; i < 24; i++) {
            System.out.println(leakBucket.leak());
        }
    }

}
