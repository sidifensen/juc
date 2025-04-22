import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Test17 {
    private static final Logger log = LoggerFactory.getLogger(Test17.class);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2,()->{//parties为参与者数目，要尽量和线程数 一致
            log.info("所有线程都到达了栅栏，继续执行");
        });// 构造函数中传入了Runnable，当计数器减到0时，会执行该Runnable


        for (int i = 0; i < 3; i++)
            executorService.submit(()->{
                log.info("开始1");
                try {
                    sleep(1000);
                    cyclicBarrier.await();// 调用一次await就会减一; 直到减到0时才会继续执行
                    log.info("结束1");
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });

            executorService.submit(()->{
                log.info("开始2");
                try {
                    sleep(2000);
                    cyclicBarrier.await();// 调用一次await就会减一; 直到减到0时才会继续执行
                    log.info("结束2");
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }
}
