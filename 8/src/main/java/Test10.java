import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractCollection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Test10 {
    private static final Logger log = LoggerFactory.getLogger(Test10.class);

    public static void main(String[] args) {
        MyLock lock = new MyLock();


        new Thread(() -> {
            lock.lock();
//            log.info("locking..");
            try {
                log.info("get lock");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                log.info("release lock");
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.info("get lock");
            } finally {
                log.info("release lock");
                lock.unlock();
            }
        }, "t2").start();

    }

}

//自定义锁,不可重入锁
class MyLock implements Lock {

    //独占锁 同步器类
    class MySync extends AbstractQueuedSynchronizer {
        @Override//尝试获取锁
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {//compareAndSetState设置状态,返回true表示设置成功
                //加上了锁,并设置owner为当前线程   exclusive 独占的
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override//释放锁
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);//state加了volatile,应该放在后面,前面会有写屏障
            return true;
        }

        @Override//是否持有独占锁
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }

    private MySync sync = new MySync();

    @Override//加锁 (会放入队列等待)
    public void lock() {
        sync.acquire(1);
    }

    @Override//加锁(可打断)
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override//尝试加锁 (只会尝试一次)
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override//尝试加锁 (带超时时间)
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override//释放锁
    public void unlock() {
        sync.release(1);//release除了会释放锁会清空owner,还会唤醒节点的后继节点（如果存在）。
    }

    @Override//创建条件变量
    public Condition newCondition() {
        return sync.newCondition();
    }
}