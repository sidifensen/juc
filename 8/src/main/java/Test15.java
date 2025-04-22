import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class Test15 {
    private static final Logger log = LoggerFactory.getLogger(Test15.class);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CountDownLatch latch = new CountDownLatch(3);//计数器为3

        executorService.submit(()->{
            log.info("begin");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.info("end {}",latch.getCount());
        });

        executorService.submit(()->{
            log.info("begin");
            try {
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.info("end {}",latch.getCount());
        });

        executorService.submit(()->{
            log.info("begin");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.info("end {}",latch.getCount());
        });

        executorService.submit(()->{
            try {
                latch.await();
                log.info("latch await");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });


    }
}
