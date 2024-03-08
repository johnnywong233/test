package utils.ratelimiter;

import lombok.AllArgsConstructor;

/**
 * Author: Johnny
 * Date: 2018/8/8
 * Time: 22:03
 */
@AllArgsConstructor
public class TokenProducer implements Runnable {

    private TokenBucket tokenBucket;

    @Override
    public void run() {
        tokenBucket.addToken();
    }
}
