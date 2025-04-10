import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Test7 {
    public static void main(String[] args) {
        Thread t = new Thread("t1"){
            @Override
            public void run() {
                try {
                    TimeUnit.DAYS.sleep(1);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("Thread t1 is running");
            }
        };

        t.start();//就绪
        log.debug("Thread t1 is ready  "+t.getState());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("Thread t1 is running   "+t.getState());

    }
}
