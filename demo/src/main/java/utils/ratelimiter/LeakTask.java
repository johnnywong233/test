package utils.ratelimiter;

import lombok.AllArgsConstructor;

/**
 * Author: Johnny
 * Date: 2018/8/9
 * Time: 10:52
 */
@AllArgsConstructor
public class LeakTask implements Runnable {

    private LeakBucket leakBucket;

    @Override
    public void run() {
        leakBucket.leak();
    }
}
