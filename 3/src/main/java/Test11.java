import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test11 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true){
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("isInterrupted: {}", interrupted);
                    break;
                }
                log.debug("sleep...");
            }

        }, "t1");

        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();

        //打断正常运行的线程, 不会清空打断状态
        log.debug("isInterrupted: {}", t1.isInterrupted());//不会清除中断状态 还是true
    }


}
