package utils.ratelimiter;

import lombok.AllArgsConstructor;

/**
 * Author: Johnny
 * Date: 2018/8/9
 * Time: 10:51
 */
@AllArgsConstructor
public class FlowTask implements Runnable {
    private LeakBucket<String> leakBucket;

    @Override
    public void run() {
        leakBucket.flow("request a");
    }

}
