package thread.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Author: Johnny
 * Date: 2016/10/26
 * Time: 23:45
 */
/*
 * 队列锁  see http://git.oschina.net/qcliu/testC/blob/master/multicore/clh.c
 *  CLHLock的思想是当前线程在前一个线程的node上spin，每个线程unlock时修改自身的标记。
 在共享总线结构下性能可以，无法应对分布式。
 unlock时必须将myNode指向前面的Node！
> 后果 ：如果Thread A unlock()后，紧接着又进入
队尾， A的locked会再次被置为TRUE， Thread B还在看着Thread A 的locked字段，于是产生
deadlock。
初始时教室里面有一个空凳子， 每个学生来到门口排队时都自己带着一个凳子。
 */
public class CLHLock implements Lock {

    //TODO: see the link to find c++ code, and write into java
    void initCLHlock() {
    }

    @Override
    public void lock() {


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

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
