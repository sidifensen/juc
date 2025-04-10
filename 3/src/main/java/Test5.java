import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test5 {
    public static void main(String[] args) {
        Thread t = new Thread("t1"){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
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
