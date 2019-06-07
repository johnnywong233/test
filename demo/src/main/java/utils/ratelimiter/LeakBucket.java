package utils.ratelimiter;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 漏桶
 * @author Johnny
 * Date: 2018/8/9
 * Time: 10:52
 */
public class LeakBucket<T> {
    private volatile String key;

    private volatile Integer limit = 3000;

    private volatile Queue<T> queue = new ArrayDeque<>(this.limit);

    boolean flow(T request) {
        return queue.add(request);
    }

    T leak() {
        return queue.poll();
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
