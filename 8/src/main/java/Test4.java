import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class Test4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Future<Integer> result1 = pool.submit(() -> {
            log.debug("task 1 running...");
            Thread.sleep(1000);
            log.debug("task 1 finish...");
            return 1;
        });

        Future<Integer> result2 = pool.submit(() -> {
            log.debug("task 2 running...");
            Thread.sleep(1000);
            log.debug("task 2 finish...");
            return 2;
        });

        Future<Integer> result3 = pool.submit(() -> {
            log.debug("task 3 running...");
            Thread.sleep(1000);
            log.debug("task 3 finish...");
            return 3;
        });

//        pool.shutdown();//关闭线程池,不再接收新的任务,但正在执行的任务会继续执行完毕

//        pool.awaitTermination(3, TimeUnit.SECONDS);//等待所有线程执行完毕,最长等待时间为3秒

        log.debug("all tasks finished");

        List<Runnable> runnables = pool.shutdownNow();//队列中的任务全部取消,并打断正在执行的线程

        log.debug("cancel all tasks"+ runnables );
    }

}
