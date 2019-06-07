package utils.ratelimiter;

/**
 * Author: Johnny
 * Date: 2018/8/8
 * Time: 22:03
 */
public class TokenProducer implements Runnable {

    private TokenBucket tokenBucket;

    TokenProducer(TokenBucket tokenBucket) {
        this.tokenBucket = tokenBucket;
    }

    @Override
    public void run() {
        tokenBucket.addToken();
    }
}
