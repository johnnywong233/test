package thread.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Author: Johnny
 * Date: 2016/10/26
 * Time: 23:41
 */
/*
 *TASLock算法的改进
 * while (state.get()){} 是一个改进，效果是先看一眼lock的状态，当lock是false时，
再真正的执行 state.getAndSet(true) 。
当state.getAndSet(true) 的return为false时，说明之前的确是false，于是获得锁，return。
否则回到while(true),再次尝试获得锁。
defect：
在unlock时，state.set(false)还是会带来大量的cache miss。
cache miss VS cache hit
 */
public class TTASLock implements Lock {
    AtomicBoolean state = new AtomicBoolean(false);

    public void lock() {
        while (true) {
            while (state.get()) {
            }
            if (!state.getAndSet(true))
                return;
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock() {
        state.set(false);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}