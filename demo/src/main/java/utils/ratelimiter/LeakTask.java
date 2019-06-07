package utils.ratelimiter;

/**
 * Author: Johnny
 * Date: 2018/8/9
 * Time: 10:52
 */
public class LeakTask implements Runnable {

    private LeakBucket leakBucket;

    LeakTask(LeakBucket leakBucket) {
        this.leakBucket = leakBucket;
    }

    @Override
    public void run() {
        leakBucket.leak();
    }
}
