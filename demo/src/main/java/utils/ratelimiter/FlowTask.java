package utils.ratelimiter;

/**
 * Author: Johnny
 * Date: 2018/8/9
 * Time: 10:51
 */
public class FlowTask implements Runnable {
    private LeakBucket<String> leakBucket;

    FlowTask(LeakBucket<String> leakBucket) {
        this.leakBucket = leakBucket;
    }

    @Override
    public void run() {
        leakBucket.flow("request a");
    }

}
