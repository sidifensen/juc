import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Test9 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("1");
        }, "t1");
        t1.start();

        new Thread(() -> {
            log.debug("2");
            LockSupport.unpark(t1);
        }, "t2").start();
    }
}
