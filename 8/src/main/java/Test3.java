import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Test3 {

    private static final Logger log = LoggerFactory.getLogger(Test3.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

//        method1(pool);

//        method2(pool);

        method3(pool);


    }

    private static void method3(ExecutorService pool) throws InterruptedException, ExecutionException {
        //如果只有一个线程,则直接返回结果2
        //如果有多个线程,则返回第一个线程执行完毕的结果 即结果3
        Object o = pool.invokeAny(Arrays.asList(() -> {
                    log.debug("task2");
                    Thread.sleep(1000);
                    return "ok2";
                }, () -> {
                    log.debug("task3");
                    Thread.sleep(500);
                    return "ok3";
                }, () -> {
                    log.debug("task4");
                    Thread.sleep(3000);
                    return "ok4";
                }
        ));
        log.debug("result: {}", o);
    }

    private static void method2(ExecutorService pool) throws InterruptedException, ExecutionException {
        List<Future<String>> task2 = pool.invokeAll(Arrays.asList(() -> {
            log.debug("task2");
            Thread.sleep(1000);
            return "ok2";
        }, () -> {
            log.debug("task3");
            Thread.sleep(500);
            return "ok3";
            }, () -> {
            log.debug("task4");
            Thread.sleep(3000);
            return "ok4";
            }
        ));

        //主线程必须等待所有子线程执行完毕 ，才能继续执行3.5秒后退出,因为只有两个线程,所以等待时间为0.5秒+3秒
        for(Future<String> future : task2){
            log.debug("result: {}", future.get());
        }
    }

    private static void method1(ExecutorService pool) throws InterruptedException, ExecutionException {
        Future<String> submit = pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.debug("task1");
                Thread.sleep(1000);
                return "ok";
            }
        });

        log.debug("result: {}", submit.get());
    }
}
