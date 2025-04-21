import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test11 {
    public static void main(String[] args) throws InterruptedException {
        DataContainer container = new DataContainer();
        new Thread(() -> {
            container.read();
        },"t1").start();

        Thread.sleep(2000);

        new Thread(() -> {
            container.write("new data");
        },"t2").start();
    }

}

class DataContainer{
    private static final Logger log = LoggerFactory.getLogger(DataContainer.class);
    public Object data;

    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

    public Object read(){
        log.info("获取读锁");
        r.lock();
        try{
            log.info("read");
            Thread.sleep(1000);
            return data;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            log.info("释放读锁");
            r.unlock();
        }
    }

    public void write(Object data){
        log.info("获取写锁");
        w.lock();
        try{
            log.info("write");
            this.data = data;
        }finally{
            log.info("释放写锁");
            w.unlock();
        }
    }
}
