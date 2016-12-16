package thread.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Author: Johnny
 * Date: 2016/10/26
 * Time: 23:47
 * MCSLock 用于解决分布式并行的问题。每个线程都在自己的node上spin，当释放锁时通知后面的线程。
 */
public class MCSLock implements Lock {

    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    public MCSLock() {
        AtomicReference<QNode> queue = new AtomicReference<>(null);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;
            //wait until predecessor gives up the lock
            while (qnode.locked) {
            }//将自己设为true然后spin，看似deadlock
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

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        if (qnode.next == null)        //后面没有等待线程的情况
        {//------there is a gap!!!!
            if (tail.compareAndSet(qnode, null))
                return;                //真的没有等待线程，则直接返回，不需要通知
            //wait until predecessor fills in its next field
            while (qnode.next == null) {
            }
        }
        //右面有等待线程，则通知后面的线程
        qnode.next.locked = false;
        qnode.next = null;

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}

class QNode {
    boolean locked = false;
    QNode next = null;//与CLHLock相比，多了这个真正的next
}