import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test14 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("结束");
        }, "t1");


        //当所有用户线程都结束时，守护线程会自动终止，即使它们还在运行
        t1.setDaemon(true);


        t1.start();

        Thread.sleep(1000);
        log.debug("结束");
    }

}
