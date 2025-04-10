import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class Test2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                log.info("Running ");
                Thread.sleep(2000);
                return "Hello World1";
            }
        });
        Thread thread2 = new Thread(futureTask, "MyThread2");
        thread2.start();
        log.info("{}",futureTask.get());
    }

}
