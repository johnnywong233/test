package cache;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by wajian on 2016/8/13.
 */
public class Test_CacheByFuture {
    //http://liushulin.iteye.com/blog/2066878
    public static void main(String[] args) {
        Test_CacheByFuture tc = new Test_CacheByFuture();
        tc.start();
    }

    //to realize cache with FutureTask and ConcurrentMap
    public void start() {
        final CacheManager cache = new CacheManager();
        ExecutorService es = Executors.newFixedThreadPool(100);
        for (int i = 1; i <= 50; i++) {
            final int key = (int) (Math.random() * 10);
            es.execute(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = cache.get(String.valueOf(key));
                System.out.println(Thread.currentThread().getName() + "get the complete result:" + result);
            });
        }

    }

    private class CacheManager {
        ConcurrentHashMap<String, FutureTask<String>> concurrentHashMap = new ConcurrentHashMap<>();

        public String get(String key) {
            String result = "default";
            FutureTask<String> task = concurrentHashMap.get(key);
            if (task == null) {
                FutureTask<String> tempTask = new FutureTask<>(() -> {
                    Thread.sleep(3 * 1000);
                    return "complete " + new Date().toLocaleString();
                });
                /*
                 * putIfAbsent：
                 * 如果没有这个key，那么放入key-value，返回null。
                 * 如果有这个key，那么返回value。
                 * 整个操作时原子性的，因为内部实现加锁了。
                 */
                /*
                 * 缓存的意义在于：一定要确保map中保存的task已经执行完成，通过get方法直接可以取出计算好的结果来。如果单纯的就是
                 * put进去一个没有执行的task，没有任何意义。所以使用putIfAbsent，第一次放进task之后去执行这个task，以后就不执行了。
                 */
                task = concurrentHashMap.putIfAbsent(key, tempTask);
                if (task == null) {
                    task = tempTask;
                    System.out.println("key=" + key + "start computing..");
                    task.run();
                }
            }
            try {
                System.out.println("start get the result of key=" + key);
                result = task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //如果get被中断，那么直接取消任务
                task.cancel(true);
                //此时暂时用不上，但是还是写上吧
                //Thread.currentThread().interrupt();
                concurrentHashMap.remove(key);
            } catch (Exception e) {
                e.printStackTrace();
                concurrentHashMap.remove(key);
            }
            return result;
        }
    }
}
