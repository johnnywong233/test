package thread.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Author: Johnny
 * Date: 2016/10/26
 * Time: 23:40
 */
/*
 * 每一个Lock带有一个状态位，lock()与unlock()操作原子的改变状态位。false时可进入，true时spin
 * defect：在锁被其他线程持有的情况下， while(state.getAndSet(true)) 会不停的将state从true改为true
 */
public class TASLock implements Lock {
    AtomicBoolean state = new AtomicBoolean(false);

    public void lock() {
        while (state.getAndSet(true)) {
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
