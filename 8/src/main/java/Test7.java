import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test7 {
    private static final Logger log = LoggerFactory.getLogger(Test7.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Boolean> submit = pool.submit(() -> {
            log.info("Task 1");
            int i = 1/0;
            return true;
        });

        log.info(String.valueOf(submit.get()));
    }
}
