import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test10 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        t1.start();
        Thread.sleep(2000);
        log.debug("interrupt");
        t1.interrupt();
        log.debug("isInterrupted: {}", t1.isInterrupted());//会清除中断状态 变成false
    }


}
