import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Test2 {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
//        ExecutorService pool = Executors.newSingleThreadExecutor( new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"MyPool-t" + t.getAndIncrement());
            }

        });

        pool.execute(() -> {
//            int i = 1/0;
            log.info("Task 1");
        });
        pool.execute(() -> {
            log.info("Task 2");
        });

        pool.execute(() -> {
            log.info("Task 3");
        });
    }
}
