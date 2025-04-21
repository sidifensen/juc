import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.StampedLock;

import static java.lang.Thread.sleep;

public class Test12 {
    public static void main(String[] args) throws InterruptedException {
        DataContainerStamped dc = new DataContainerStamped(1);
        new Thread(()->{
            try {
                dc.read(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t1").start();

//        sleep(1000);
        new Thread(()->{
            try {
//                dc.read(0);
                dc.write(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t2").start();

    }

}

class DataContainerStamped{

    private static final Logger log = LoggerFactory.getLogger(DataContainerStamped.class);
    private int data;


    private final StampedLock lock = new StampedLock();
    public DataContainerStamped(int data){
        this.data = data;
    }

    public int read(int readTime) throws InterruptedException {
        long stamp = lock.tryOptimisticRead();//乐观读锁
        log.debug("optimistic read lock {}",stamp);

        sleep(readTime);
        if(lock.validate(stamp)){//验证是否有写锁 (戳的校验)
            log.debug("read finished {}",stamp);
            return data;
        }

        //如果失效了

        //锁升级,从乐观读锁升级到读锁
        try{
            stamp = lock.readLock();
            log.debug("read lock {}",stamp);
            sleep(readTime);
            return data;
        }finally {
            log.debug("unlock read lock {}",stamp);
            lock.unlockRead(stamp);//释放读锁
        }
    }

    public void write(int newData) throws InterruptedException {
        long stamp = lock.writeLock();

        log.debug("write lock {}",stamp);

        try{
            sleep(1000);
        }finally {
            log.debug("unlock write lock {}",stamp);
            lock.unlockWrite(stamp);
        }

    }




}
