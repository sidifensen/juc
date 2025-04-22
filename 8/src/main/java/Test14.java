import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;

public class Test14 {
    private static final Logger log = LoggerFactory.getLogger(Test14.class);

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);//计数器为3

        new Thread(()->{
            log.info("干活");
            try {
                sleep(1000);
                latch.countDown();
                log.info("干完活了");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(()->{
            log.info("干活");
            try {
                sleep(2000);
                latch.countDown();
                log.info("干完活了");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(()->{
            log.info("干活");
            try {
                sleep(4000);
                latch.countDown();
                log.info("干完活了");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        log.info("Starting");
        latch.await();//阻塞等待，直到latch计数器为0才继续执行
        log.info("All done");
    }
}
