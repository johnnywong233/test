package utils.ratelimiter;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Johnny
 * Date: 2018/8/8
 * Time: 22:01
 * 令牌桶核心算法，每个请求去领取token，拿到token然后继续
 */
@Data
@Builder
public class TokenBucket {
    /**
     * 唯一标识
     */
    private String key;

    /**
     * 桶的大小
     */
    private Integer limit;

    /**
     * 桶当前的token
     */
    private volatile AtomicInteger tokens;

    /**
     * 加token
     */
    void addToken() {
        if (tokens == null) {
            tokens = new AtomicInteger(0);
        }
        tokens.incrementAndGet();
    }

    /**
     * 减token
     */
    public void delToken() {
        tokens.decrementAndGet();
    }

    public synchronized boolean getToken() {
        if (tokens == null) {
            tokens = new AtomicInteger(0);
        }
        return tokens.intValue() > 0 && tokens.decrementAndGet() > 0;
    }

}
