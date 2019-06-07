package utils.ratelimiter;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单的速率限制器
 *
 * @author Johnny
 * Date: 2018/8/8
 * Time: 21:22
 */
@Data
@Builder
public class Bucket {
    /**
     * 指定的请求
     */
    private volatile String key;

    /**
     * 开始时间
     */
    private volatile Long start;

    /**
     * 定时时长
     */
    private volatile Long interval;

    /**
     * 当前次数
     */
    private volatile AtomicInteger count;

    /**
     * 请求次数
     */
    private volatile Integer limit;

    public static void main(String[] args) {
        Bucket bucket = Bucket.builder().key("request a").limit(5).interval(2000L).build();
        for (int i = 0; i < 15; i++) {
            bucket.request();
        }
    }

    public boolean request() {
        if (start == null) {
            start = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - start <= interval) {
            if (count == null) {
                count = new AtomicInteger(0);
            }
            if (count.intValue() <= limit) {
                count.incrementAndGet();
                return true;
            } else {
                System.out.println(key + "被拒绝访问");
                return false;
            }
        } else {
            start = System.currentTimeMillis();
            return request();
        }
    }

}
