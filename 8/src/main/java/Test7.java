import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class Test7 {
    private static final Logger log = LoggerFactory.getLogger(Test7.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Boolean> submit = pool.submit(() -> {
            log.info("Task 1");
            int i = 1/0;
            return true;
        });//submit会吞异常,通过get()方法获取异常信息

        pool.execute(()->{
            log.info("Task 2");
            int i = 1/0;
        });//execute不会吞异常,会打印异常信息到日志中

        log.info(String.valueOf(submit.get()));

    }
}
