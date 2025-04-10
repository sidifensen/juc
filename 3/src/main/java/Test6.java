import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test6 {
    public static void main(String[] args) {
        Thread t = new Thread("t1"){
            @Override
            public void run() {
                try {
                    log.debug("准备睡眠");
                    Thread.sleep(3000);
                    log.debug("睡眠结束");
                } catch (InterruptedException e) {
                    log.debug("Thread t1 is interrupted");
                    throw new RuntimeException(e);
                }
                log.info("Thread t1 is running");
            }
        };

        t.start();//就绪
        log.debug("Thread t1 is ready  "+t.getState());

        try {
            Thread.sleep(1000);
            log.debug("interrupt Thread t1");
            t.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("Thread t1 is running   "+t.getState());

    }
}
