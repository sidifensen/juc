import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Test6 {
    private static final Logger log = LoggerFactory.getLogger(Test6.class);

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        pool.scheduleAtFixedRate(()->{
            log.info("running");
        },1,1, TimeUnit.SECONDS);//initDelay是第一次执行任务的延迟时间，period是任务执行间隔时间
        //如果任务耗时大于间隔时间,则会等待任务执行完毕才会执行下一个任务

        pool.scheduleWithFixedDelay(()->{
            log.info("running");
        },1,1, TimeUnit.SECONDS);//initDelay是第一次执行任务的延迟时间，delay是任务执行完毕后再次执行任务的延迟时间
        //如果线程休眠了两秒,则会从结束后开始算,再加上1秒钟的延迟时间,才会执行下一个任务

//        pool.schedule(()->{
//                log.info("task1");
//            try {
//                sleep(2000);//当只有一个核心线程时，会等待任务执行完毕才会执行下一个任务
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//        },1, TimeUnit.SECONDS);
//        pool.schedule(()->{
//            log.info("task2");
//        },1, TimeUnit.SECONDS);
    }
}
