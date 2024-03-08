package utils.ratelimiter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持多个请求
 *
 * @author Johnny
 * Date: 2018/8/8
 * Time: 21:59
 */
public class RateLimiter {
    /**
     * 支持多个请求
     */
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>(300);

    public boolean getKey(String key) {
        Bucket bucket = buckets.get(key);
        if (bucket == null) {
            Bucket bucket1 = Bucket.builder().key(key).limit(12).interval(2000L).build();
            bucket = bucket1;
            buckets.put(key, bucket1);
        }
        return bucket.request();
    }

    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateLimiter();
        for (int i = 0; i < 14; i++) {
            rateLimiter.getKey("request a");
        }
    }
}
